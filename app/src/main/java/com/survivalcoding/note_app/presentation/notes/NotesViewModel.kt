package com.survivalcoding.note_app.presentation.notes

import androidx.lifecycle.*
import com.survivalcoding.note_app.domain.model.Note
import com.survivalcoding.note_app.domain.repository.NoteRepository
import com.survivalcoding.note_app.domain.use_case.GetNotesUseCase
import com.survivalcoding.note_app.domain.use_case.NoteUseCases
import com.survivalcoding.note_app.domain.util.NoteOrder
import com.survivalcoding.note_app.domain.util.OrderType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NotesViewModel(
    private val useCases: NoteUseCases,
) : ViewModel(), LifecycleEventObserver {

    private val _state = MutableStateFlow(NotesState())
    val state: LiveData<NotesState> = _state.asLiveData()

    private var recentlyDeletedNote: Note? = null

    var noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    useCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.Order -> {
                noteOrder = event.noteOrder
                getNotes()
            }
            NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    useCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            NotesEvent.ToggleOrderSection -> {
                _state.value = _state.value.copy(
                    isOrderSectionVisible = !_state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes() {
        useCases.getNotes(noteOrder)
            .onEach { notes ->
                _state.value = _state.value.copy(
                    notes = notes,
                )
            }
            .launchIn(viewModelScope)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_START) {
            getNotes()
        }
    }
}