package com.survivalcoding.noteapp.presentation.notes

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.Order

data class NotesUiState(
    val noteList: List<Note>,
    val orderBy: Order
)