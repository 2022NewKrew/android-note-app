package com.survivalcoding.note_app.presentation.notes

import androidx.lifecycle.*
import com.survivalcoding.note_app.domain.model.Note
import com.survivalcoding.note_app.domain.repository.NoteRepository
import com.survivalcoding.note_app.domain.use_case.GetNotesUseCase
import com.survivalcoding.note_app.domain.util.NoteOrder
import com.survivalcoding.note_app.domain.util.OrderType
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NotesViewModel(
    private val repository: NoteRepository,
    private val getNotesUseCase: GetNotesUseCase,
) : ViewModel(), LifecycleEventObserver {

    private val _state = MutableLiveData(NotesState())
    val state: LiveData<NotesState> = _state

    private var recentlyDeletedNote: Note? = null

    var noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    repository.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.Order -> {
                noteOrder = event.noteOrder
                getNotes()
            }
            NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    repository.insertNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            NotesEvent.ToggleOrderSection -> {
                _state.value = state.value!!.copy(
                    isOrderSectionVisible = !state.value!!.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes() {
        getNotesUseCase(noteOrder)
            .onEach { notes ->
                _state.value = state.value!!.copy(
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