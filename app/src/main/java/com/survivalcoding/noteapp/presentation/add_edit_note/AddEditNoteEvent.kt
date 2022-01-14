package com.survivalcoding.noteapp.presentation.add_edit_note

import com.survivalcoding.noteapp.domain.model.NoteColor

sealed class AddEditNoteEvent {
    class SetId(val id: Long?) : AddEditNoteEvent()
    class SetTitle(val title: String) : AddEditNoteEvent()
    class SetContent(val content: String) : AddEditNoteEvent()
    class SetColor(val color: NoteColor) : AddEditNoteEvent()
    object InsertNote : AddEditNoteEvent()
}