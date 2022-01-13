package com.survivalcoding.noteapp.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.usecase.GetFlowOrderByUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class NotesViewModel(
    private val noteRepository: NoteRepository,
    getNotesOrderByUseCase: GetFlowOrderByUseCase<Note>
) : ViewModel() {


    private val _orderBy = MutableStateFlow(NoteOrderBy.TITLE)
    private val _isAsc = MutableStateFlow(true)
    private val _notes = getNotesOrderByUseCase(_orderBy.map { it.toComparator() }, _isAsc)
    private val _deletedNote = MutableStateFlow<Note?>(null)

    val uiState =
        combine(_orderBy, _isAsc, _notes,_deletedNote) { orderBy, isAsc, notes, deletedNote ->
            NotesUiState(orderBy, isAsc, notes, deletedNote)
        }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.delete(note)
            _deletedNote.value = note
        }
    }

    fun undoDelete() {
        viewModelScope.launch {
            _deletedNote.value?.let { noteRepository.upsert(it) }
            _deletedNote.value = null
        }
    }

    fun setDeletedNoteToNull() {
        _deletedNote.value = null
    }

    fun setOrderBy(orderBy: NoteOrderBy) {
        _orderBy.value = orderBy
    }

    fun setIsAsc(isAsc: Boolean) {
        _isAsc.value = isAsc
    }
}