package com.survivalcoding.noteapp.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Note
import com.example.domain.usecase.DeleteNodeUseCase
import com.example.domain.usecase.GetNoteAllUseCase
import com.example.domain.usecase.GetNoteByIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NotesViewModel(
    private val getNoteAllUseCase: GetNoteAllUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val deleteNodeUseCase: DeleteNodeUseCase
) : ViewModel() {

    val notes: Flow<List<Note>> = getNoteAllUseCase()

    /*
     fun searchSingleNote(id: Long) {

     }
     */

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNodeUseCase(note)
        }
    }


}