package com.survivalcoding.noteapp.presentation.notes

import androidx.lifecycle.*
import com.example.domain.entity.Note
import com.example.domain.repository.NoteRepository
import com.example.domain.usecase.DeleteNodeUseCase
import com.example.domain.usecase.GetNoteAllUseCase
import com.example.domain.usecase.GetNoteByIdUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class NotesViewModel(
    private val getNoteAllUseCase: GetNoteAllUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val deleteNodeUseCase: DeleteNodeUseCase,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    val notes: Flow<List<Note>> = getNoteAllUseCase()

    /*
     fun searchSingleNote(id: Long) {

     }
     */

    fun deleteNote(note: Note) {
        viewModelScope.launch(coroutineDispatcher) {
            deleteNodeUseCase(note)
        }
    }
}

class NotesViewModelFactory(
    private val repository: NoteRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            return NotesViewModel(
                GetNoteAllUseCase(repository),
                GetNoteByIdUseCase(repository),
                DeleteNodeUseCase(repository),
                Dispatchers.IO
            ) as T
        } else throw IllegalArgumentException()
    }

}