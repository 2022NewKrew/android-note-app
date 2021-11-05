package com.survivalcoding.note_app.presentation.add_edit_note

sealed class AddEditNoteEvent {
    data class SaveNote(val title: String, val content: String) : AddEditNoteEvent() {
        var id: Int? = null
    }

    data class ChangeColor(val color: Int) : AddEditNoteEvent()
}