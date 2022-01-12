package com.survivalcoding.noteapp.presentation.notes

import android.app.Application
import androidx.lifecycle.*
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NotesRepository
import com.survivalcoding.noteapp.presentation.notes.NotesFragment.Companion.BY_COLOR
import com.survivalcoding.noteapp.presentation.notes.NotesFragment.Companion.BY_DATE
import com.survivalcoding.noteapp.presentation.notes.NotesFragment.Companion.BY_TITLE
import com.survivalcoding.noteapp.presentation.notes.NotesFragment.Companion.SORT_ASC
import kotlinx.coroutines.launch

class NotesViewModel(
    application: Application,
    private val notesRepository: NotesRepository
) : AndroidViewModel(application) {
    private var _notes: MutableLiveData<List<Note>> = MutableLiveData()
    val notes: LiveData<List<Note>> = _notes
    private var deletedNote: Note? = null

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
        deletedNote = note
    }

    fun restoreNote() {
        viewModelScope.launch {
            deletedNote?.let {
                notesRepository.insertNote(it)
                _notes.value = notesRepository.getNotes()
            }
        }
        setDeletedNull()
    }

    fun setDeletedNull() {
        deletedNote = null
    }

    fun sortNotes(filter: Int, sort: Int) {
        viewModelScope.launch {
            val sortingNotes = _notes.value ?: notesRepository.getNotes()
            _notes.value = sorting(sortingNotes, filter, sort)
        }
    }

    private fun sorting(sortingNotes: List<Note>, filter: Int, sort: Int): List<Note> {
        return when (filter) {
            BY_TITLE -> {
                if (sort == SORT_ASC) sortingNotes.sortedBy { it.title }
                else sortingNotes.sortedByDescending { it.title }
            }
            BY_DATE -> {
                if (sort == SORT_ASC) sortingNotes.sortedBy { it.timestamp }
                else sortingNotes.sortedByDescending { it.timestamp }
            }
            BY_COLOR -> {
                if (sort == SORT_ASC) sortingNotes.sortedBy { it.color }
                else sortingNotes.sortedByDescending { it.color }
            }
            else -> sortingNotes
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