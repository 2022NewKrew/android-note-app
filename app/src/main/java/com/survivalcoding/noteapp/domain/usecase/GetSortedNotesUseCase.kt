package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.Order
import com.survivalcoding.noteapp.domain.model.SortBy
import com.survivalcoding.noteapp.domain.repository.NotesRepository

class GetSortedNotesUseCase(private val repository: NotesRepository) {
    suspend operator fun invoke(filter: SortBy, sort: Order): List<Note> {
        val sortingNotes = repository.getNotes()

        return when (filter) {
            SortBy.BY_TITLE -> {
                if (sort == Order.ASC) sortingNotes.sortedBy { it.title }
                else sortingNotes.sortedByDescending { it.title }
            }
            SortBy.BY_DATE -> {
                if (sort == Order.ASC) sortingNotes.sortedBy { it.timestamp }
                else sortingNotes.sortedByDescending { it.timestamp }
            }
            SortBy.BY_COLOR -> {
                if (sort == Order.ASC) sortingNotes.sortedBy { it.color }
                else sortingNotes.sortedByDescending { it.color }
            }
        }
    }
}

