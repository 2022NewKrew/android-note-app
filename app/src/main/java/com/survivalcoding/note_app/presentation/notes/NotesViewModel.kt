package com.survivalcoding.note_app.presentation.notes

import androidx.lifecycle.*
import com.survivalcoding.note_app.domain.model.Note
import com.survivalcoding.note_app.domain.repository.NoteRepository
import com.survivalcoding.note_app.domain.util.NoteOrder
import com.survivalcoding.note_app.domain.util.OrderType
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NotesViewModel(
    private val repository: NoteRepository,
) : ViewModel(), LifecycleEventObserver {

    private val _state = MutableLiveData(NotesState())
    val state: LiveData<NotesState> = _state

    private var recentlyDeletedNote: Note? = null

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    repository.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.Order -> {
                if (state.value!!.noteOrder::class == event.noteOrder::class &&
                    state.value!!.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                _state.value = state.value!!.copy(
                    noteOrder = event.noteOrder
                )
                getNotes()
            }
            NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    repository.insertNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
//            NotesEvent.ToggleOrderSection -> {
//                _state.value = state.value.copy(
//                    isOrderSectionVisible = !state.value.isOrderSectionVisible
//                )
//            }
        }
    }

    private fun getNotes() {
        val noteOrder = _state.value!!.noteOrder

        repository.getNotes()
            .map { notes ->
                when (noteOrder.orderType) {
                    OrderType.Ascending -> {
                        when (noteOrder) {
                            is NoteOrder.Color -> notes.sortedBy { it.color }
                            is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                            is NoteOrder.Title -> notes.sortedBy { it.title }
                        }
                    }
                    OrderType.Descending -> {
                        when (noteOrder) {
                            is NoteOrder.Color -> notes.sortedByDescending { it.color }
                            is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                            is NoteOrder.Title -> notes.sortedByDescending { it.title }
                        }
                    }
                }
            }
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