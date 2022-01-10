package com.survivalcoding.noteapp.presentation.add_edit_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Note
import com.example.domain.usecase.InsertNodeUseCase
import com.example.domain.usecase.UpdateNoteUseCase
import kotlinx.coroutines.launch

class AddEditNoteViewModel(
    private val insertNodeUseCase: InsertNodeUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
) : ViewModel() {


    fun updateNote(note: Note){
        viewModelScope.launch {
            updateNoteUseCase(note)
        }
    }

    fun insertNote(note: Note){
        viewModelScope.launch {
            insertNodeUseCase(note)
        }
    }

}