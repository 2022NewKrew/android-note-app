package com.survivalcoding.noteapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.survivalcoding.noteapp.NoteApplication
import com.survivalcoding.noteapp.domain.usecase.GetFlowOrderByUseCase
import com.survivalcoding.noteapp.presentation.note.NoteViewModel
import com.survivalcoding.noteapp.presentation.notes.NotesViewModel

class AppViewModelFactory(private val noteApplication: NoteApplication) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NotesViewModel::class.java) -> {
                NotesViewModel(
                    noteApplication.noteRepository,
                    GetFlowOrderByUseCase(noteApplication.noteRepository)
                ) as T
            }
            modelClass.isAssignableFrom(NoteViewModel::class.java) -> {
                NoteViewModel(noteApplication.noteRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel Class ${modelClass.canonicalName}")
            }
        }
    }
}