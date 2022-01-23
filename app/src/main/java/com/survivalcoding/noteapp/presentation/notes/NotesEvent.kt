package com.survivalcoding.noteapp.presentation.notes

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.SortKey
import com.survivalcoding.noteapp.domain.model.SortMode

sealed class NotesEvent {
    class SetSortKey(val key: SortKey) : NotesEvent()
    class SetSortMode(val mode: SortMode) : NotesEvent()
    class SetVisibility(val visibility: Int) : NotesEvent()
    class DeleteNote(val note: Note) : NotesEvent()
    object UndoDelete : NotesEvent()
}
