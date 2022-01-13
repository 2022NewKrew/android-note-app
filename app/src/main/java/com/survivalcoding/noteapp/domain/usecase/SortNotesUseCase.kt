package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NotesRepository
import com.survivalcoding.noteapp.presentation.notes.NotesFragment

class SortNotesUseCase(private val repository: NotesRepository) {
    suspend operator fun invoke(filter: Int, sort: Int): List<Note> {
        val sortingNotes = repository.getNotes()

        return when (filter) {
            NotesFragment.BY_TITLE -> {
                if (sort == NotesFragment.SORT_ASC) sortingNotes.sortedBy { it.title }
                else sortingNotes.sortedByDescending { it.title }
            }
            NotesFragment.BY_DATE -> {
                if (sort == NotesFragment.SORT_ASC) sortingNotes.sortedBy { it.timestamp }
                else sortingNotes.sortedByDescending { it.timestamp }
            }
            NotesFragment.BY_COLOR -> {
                if (sort == NotesFragment.SORT_ASC) sortingNotes.sortedBy { it.color }
                else sortingNotes.sortedByDescending { it.color }
            }
            else -> sortingNotes
        }
    }
}