package com.survivalcoding.note_app.presentation.notes

import androidx.lifecycle.*
import com.survivalcoding.note_app.domain.model.Note
import com.survivalcoding.note_app.domain.repository.NoteRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NotesViewModel(
    private val repository: NoteRepository,
) : ViewModel(), LifecycleObserver {
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
//            is NotesEvent.Order -> {
//                if (state.value.noteOrder::class == event.noteOrder::class &&
//                        state.value.noteOrder.orderType == event.noteOrder.orderType) {
//                    return
//                }
//                getNotes(event.noteOrder)
//            }
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

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun getNotes() {
        repository.getNotes()
            .map { notes ->
                notes.sortedByDescending { it.timestamp }
            }
            .onEach { notes ->
                _state.value = state.value!!.copy(
                    notes = notes,
                )
            }
            .launchIn(viewModelScope)
    }
}