package com.survivalcoding.noteapp.presentation.notes

import android.app.Application
import androidx.lifecycle.*
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NotesRepository
import com.survivalcoding.noteapp.domain.usecase.SortNotesUseCase
import kotlinx.coroutines.launch

class NotesViewModel(
    application: Application,
    private val notesRepository: NotesRepository,
    private val sortNotesUseCase: SortNotesUseCase,
) : AndroidViewModel(application) {
    private var _notes: MutableLiveData<List<Note>> = MutableLiveData()
    val notes: LiveData<List<Note>> = _notes

    init {
        viewModelScope.launch {
            _notes.value = notesRepository.getNotes()
        }
    }

    suspend fun getNoteById(id: Int): Note? = notesRepository.getNoteById(id)

    fun insertNote(note: Note) {
        viewModelScope.launch {
            notesRepository.insertNote(note)
            _notes.value = notesRepository.getNotes()
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notesRepository.deleteNote(note)
            _notes.value = notesRepository.getNotes()
        }
    }

    fun sortNotes(filter: Int, sort: Int) {
        viewModelScope.launch {
            _notes.value = sortNotesUseCase.invoke(filter, sort)
        }
    }
}

class NotesViewModelFactory(
    private val application: Application,
    private val notesRepository: NotesRepository,
) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java))
            return NotesViewModel(
                application = application,
                notesRepository = notesRepository,
                sortNotesUseCase = SortNotesUseCase(notesRepository)
            ) as T
        else throw IllegalArgumentException()
    }
}