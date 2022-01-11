package com.survivalcoding.noteapp.add_edit_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Note
import com.example.domain.repository.NoteRepository
import com.example.domain.usecase.*
import com.survivalcoding.noteapp.presentation.notes.NotesViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class AddEditNoteViewModel(
    private val insertNodeUseCase: InsertNodeUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {


    fun updateNote(note: Note){
        viewModelScope.launch(coroutineDispatcher) {
            updateNoteUseCase(note)
        }
    }

    fun insertNote(note: Note){
        viewModelScope.launch(coroutineDispatcher) {
            insertNodeUseCase(note)
        }
    }

}

class AddEditNoteViewModelFactory(
    private val repository: NoteRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEditNoteViewModel::class.java)) {
            return AddEditNoteViewModel(
                InsertNodeUseCase(repository),
                UpdateNoteUseCase(repository),
                Dispatchers.IO
            ) as T
        } else throw IllegalArgumentException()
    }

}