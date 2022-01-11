package com.survivalcoding.noteapp.presentation.notes

import androidx.lifecycle.*
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.Order
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class NotesViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _notesUiState = MutableLiveData<NotesUiState>()
    val notesUiState: LiveData<NotesUiState> = _notesUiState

    init {
        viewModelScope.launch {
//            _notesUiState.value = NotesUiState(noteList =, orderBy = Order.defaultOrder)
        }
    }

    fun setOrder(order: Order) {
        _notesUiState.value = _notesUiState.value?.copy(orderBy = order)
        getNotesListOrderBy(order)
    }

    private fun getNotesListOrderBy(order: Order) {
        viewModelScope.launch {
//            _notesUiState.value = _notesUiState.value?.copy(noteList = , orderBy = order)
        }
    }

    private fun sendEvent(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        class NavigateToEditNote(val note: Note)
        class NavigateToAddNote
    }
}

@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory(private val noteRepository: NoteRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            return NotesViewModel(noteRepository) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}