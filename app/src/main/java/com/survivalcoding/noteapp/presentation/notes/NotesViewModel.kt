package com.survivalcoding.noteapp.presentation.notes

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.SortKey
import com.survivalcoding.noteapp.domain.model.SortMode
import com.survivalcoding.noteapp.domain.model.UIState
import com.survivalcoding.noteapp.domain.usecase.DeleteNoteUseCase
import com.survivalcoding.noteapp.domain.usecase.GetSortedNotesUseCase
import com.survivalcoding.noteapp.domain.usecase.InsertNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class NotesViewModel(
    getSortedNotesUseCase: GetSortedNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
) : ViewModel() {
    private val _sortKey = MutableStateFlow(SortKey.TITLE)
    private val _sortMode = MutableStateFlow(SortMode.ASCENDING)
    private val _notes = getSortedNotesUseCase(_sortKey.map { it.toComparator() }, _sortMode)
    private val _deletedNote = MutableStateFlow<Note?>(null)
    private val _visibility = MutableStateFlow(View.GONE)

    val uiState = combine(
        _sortKey, _sortMode, _notes, _visibility
    ) { sortKey, sortMode, notes, visibility ->
        UIState(sortKey, sortMode, notes, visibility)
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> viewModelScope.launch {
                deleteNoteUseCase(event.note)
                _deletedNote.value = event.note
            }
            is NotesEvent.SetSortKey -> _sortKey.value = event.key
            is NotesEvent.SetSortMode -> _sortMode.value = event.mode
            is NotesEvent.SetVisibility -> _visibility.value = event.visibility
            NotesEvent.UndoDelete -> viewModelScope.launch {
                _deletedNote.value?.let { insertNoteUseCase(it) }
                _deletedNote.value = null
            }
        }
    }
}