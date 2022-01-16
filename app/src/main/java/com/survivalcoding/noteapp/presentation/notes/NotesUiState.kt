package com.survivalcoding.noteapp.presentation.notes

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.Order
import com.survivalcoding.noteapp.domain.model.SortBy

data class NotesUiState(
    var notes: List<Note>,
    var sortBy: SortBy,
    var order: Order,
)