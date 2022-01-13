package com.survivalcoding.noteapp.presentation.notes

import com.survivalcoding.noteapp.domain.model.Note

enum class NoteOrderBy {
    TITLE,
    DATE,
    COLOR;

    fun toComparator(): Comparator<Note> {
        return when (this) {
            TITLE -> compareBy { it.title }
            DATE -> compareBy { it.timestamp }
            COLOR -> compareBy { it.color }
        }
    }
}