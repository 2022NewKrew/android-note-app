package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NotesRepository

class SortNotesUseCase(private val repository: NotesRepository) {
    suspend operator fun invoke(filter: Int, sort: Int): List<Note> {
        val sortingNotes = repository.getNotes()

        return when (filter) {
            BY_TITLE -> {
                if (sort == SORT_ASC) sortingNotes.sortedBy { it.title }
                else sortingNotes.sortedByDescending { it.title }
            }
            BY_DATE -> {
                if (sort == SORT_ASC) sortingNotes.sortedBy { it.timestamp }
                else sortingNotes.sortedByDescending { it.timestamp }
            }
            BY_COLOR -> {
                if (sort == SORT_ASC) sortingNotes.sortedBy { it.color }
                else sortingNotes.sortedByDescending { it.color }
            }
            else -> sortingNotes
        }
    }

    companion object {
        const val SORT_ASC = 0
        const val SORT_DESC = 1

        const val BY_TITLE = 0
        const val BY_DATE = 1
        const val BY_COLOR = 2
    }
}

