package com.survivalcoding.noteapp.presentation.notes

import android.view.View
import androidx.lifecycle.*
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.Order
import com.survivalcoding.noteapp.domain.usecase.DeleteNoteUseCase
import com.survivalcoding.noteapp.domain.usecase.GetNotesByOrderUseCase
import com.survivalcoding.noteapp.domain.usecase.InsertNoteUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class NotesViewModel(
    private val getNotesByOrderUseCase: GetNotesByOrderUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val insertNoteUseCase: InsertNoteUseCase
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _notesUiState = MutableLiveData<NotesUiState>()
    val notesUiState: LiveData<NotesUiState> = _notesUiState

    fun loadList() {
        getNotesListOrderBy(notesUiState.value?.orderBy ?: Order.defaultOrder)
    }

    fun setOrder(order: Order) {
        _notesUiState.value = _notesUiState.value?.copy(orderBy = order)
        getNotesListOrderBy(order)
    }

    private fun getNotesListOrderBy(order: Order) {
        viewModelScope.launch {
            _notesUiState.value = NotesUiState(
                noteList = getNotesByOrderUseCase(Order.defaultOrder),
                orderBy = order
            )
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase(note)
            getNotesListOrderBy(notesUiState.value?.orderBy ?: Order.defaultOrder)
            sendEvent(Event.ShowSnackBarEvent(R.string.note_deleted, R.string.undo) {
                restoreNote(note)
            })
        }
    }

    private fun restoreNote(note: Note) {
        viewModelScope.launch {
            insertNoteUseCase(note)
            getNotesListOrderBy(notesUiState.value?.orderBy ?: Order.defaultOrder)
        }
    }

    fun navigateToAddNote() {
        sendEvent(Event.NavigateToAddNote)
    }

    fun navigateToEditNote(note: Note) {
        sendEvent(Event.NavigateToEditNote(note))
    }

    private fun sendEvent(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        class NavigateToEditNote(val note: Note) : Event()
        object NavigateToAddNote : Event()
        class ShowSnackBarEvent(
            val messageResourceId: Int,
            val actionTextResourceId: Int?,
            val action: View.OnClickListener?
        ) : Event()
    }
}

@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory(
    private val getNotesByOrderUseCase: GetNotesByOrderUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val insertNoteUseCase: InsertNoteUseCase
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            return NotesViewModel(getNotesByOrderUseCase, deleteNoteUseCase, insertNoteUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}