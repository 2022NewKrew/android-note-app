package com.survivalcoding.noteapp.presentation.note

import com.survivalcoding.noteapp.domain.model.Note

data class NoteUiState(
    val note:Note = Note(),
    val saved: Boolean = false
)