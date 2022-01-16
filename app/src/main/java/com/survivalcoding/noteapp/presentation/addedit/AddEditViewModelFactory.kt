package com.survivalcoding.noteapp.presentation.addedit

import androidx.lifecycle.*
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.usecase.GetNoteByIdUseCase
import com.survivalcoding.noteapp.domain.usecase.InsertNoteUseCase

@Suppress("UNCHECKED_CAST")
class AddEditViewModelFactory(
    private val repository: NoteRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEditViewModel::class.java))
            return AddEditViewModel(
                InsertNoteUseCase(repository),
                GetNoteByIdUseCase(repository)
            ) as T
        else throw IllegalArgumentException()
    }
}