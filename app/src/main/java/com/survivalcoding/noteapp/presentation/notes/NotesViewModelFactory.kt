package com.survivalcoding.noteapp.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.usecase.DeleteNoteUseCase
import com.survivalcoding.noteapp.domain.usecase.GetSortedNotesUseCase
import com.survivalcoding.noteapp.domain.usecase.InsertNoteUseCase

@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory(
    private val repository: NoteRepository,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java))
            return NotesViewModel(
                getSortedNotesUseCase = GetSortedNotesUseCase(repository),
                deleteNoteUseCase = DeleteNoteUseCase(repository),
                insertNoteUseCase = InsertNoteUseCase(repository),
            ) as T
        else throw IllegalArgumentException()
    }
}