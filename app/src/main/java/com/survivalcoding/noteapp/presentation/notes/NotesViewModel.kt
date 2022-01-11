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
    private var _deletedNote = MutableLiveData<Note?>()
    val deletedNote: LiveData<Note?> = _deletedNote

    suspend fun getNoteById(id: Int): Note? = notesRepository.getNoteById(id)

    fun insertNote(note: Note) {
        viewModelScope.launch {
            notesRepository.insertNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notesRepository.deleteNote(note)
            _deletedNote.value = note
        }
    }

    fun restoreNote() {
        viewModelScope.launch {
            _deletedNote.value?.let {
                notesRepository.insertNote(it)
            }
            _deletedNote.value = null
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