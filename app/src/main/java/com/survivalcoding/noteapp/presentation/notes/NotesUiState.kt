package com.survivalcoding.noteapp.presentation.notes

import com.survivalcoding.noteapp.domain.NoteOrderBy
import com.survivalcoding.noteapp.domain.model.Note

data class NotesUiState(
    val orderBy: NoteOrderBy = NoteOrderBy.TITLE,
    val isAsc: Boolean = true,
    val notes: List<Note> = listOf(),
)
