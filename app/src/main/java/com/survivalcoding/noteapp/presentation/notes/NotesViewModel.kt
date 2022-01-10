package com.survivalcoding.noteapp.presentation.notes

import android.app.Application
import androidx.lifecycle.*
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NotesRepository
import kotlinx.coroutines.launch

class NotesViewModel(
    application: Application,
    private val notesRepository: NotesRepository
) : AndroidViewModel(application) {
    val notes: LiveData<List<Note>> = notesRepository.getNotes().asLiveData()

    suspend fun getNoteById(id: Int): Note? = notesRepository.getNoteById(id)

    fun insertNote(note: Note) {
        viewModelScope.launch {
            notesRepository.insertNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notesRepository.deleteNote(note)
        }
    }
}

class NotesViewModelFactory(
    private val application: Application,
    private val notesRepository: NotesRepository
) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java))
            return NotesViewModel(
                application = application,
                notesRepository = notesRepository
            ) as T
        else throw IllegalArgumentException()
    }
}