package com.survivalcoding.note_app.presentation.notes

import com.survivalcoding.note_app.domain.model.Note
import com.survivalcoding.note_app.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    object RestoreNote : NotesEvent()
    object ToggleOrderSection : NotesEvent()
}
