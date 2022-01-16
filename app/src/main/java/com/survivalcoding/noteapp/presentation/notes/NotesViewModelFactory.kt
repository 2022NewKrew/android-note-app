package com.survivalcoding.noteapp.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.usecase.*

@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory(
    private val repository: NoteRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java))
            return NotesViewModel(
                DeleteNoteUseCase(repository),
                GetNotesUseCase(
                    SortByColorAscUseCase(repository),
                    SortByColorDescUseCase(repository),
                    SortByTimestampAscUseCase(repository),
                    SortByTimestampDescUseCase(repository),
                    SortByTitleAscUseCase(repository),
                    SortByTitleDescUseCase(repository),
                ),
                InsertNoteUseCase(repository)
            ) as T
        else throw IllegalArgumentException()
    }
}