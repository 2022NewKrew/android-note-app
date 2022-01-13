package com.survivalcoding.noteapp.presentation.notes

import com.survivalcoding.noteapp.domain.model.Note

data class NotesUiState(
    var notes: List<Note>,
    var filter: Int,
    var sort: Int,
)