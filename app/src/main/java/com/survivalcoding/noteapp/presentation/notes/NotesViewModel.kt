package com.survivalcoding.noteapp.presentation.notes

import android.view.View
import androidx.lifecycle.*
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.launch

class NotesViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    private val _notes = MutableLiveData<List<Note>>(listOf())
    val notes: LiveData<List<Note>> = _notes

    var key = ORDER_TITLE
    var mode = ORDER_ASC
    var filter = FILTER_CLOSE

    init {
        refreshNoteList()
    }

    fun refreshNoteList() {
        viewModelScope.launch {
            _notes.value = noteRepository.getNotes()
            sortNotes()
        }
    }

    fun sortNotes() {
        viewModelScope.launch {
            val list = _notes.value ?: listOf()
            _notes.value =
                when (mode) {
                    ORDER_ASC -> {
                        when (key) {
                            ORDER_TITLE -> list.sortedBy { it.title }
                            ORDER_TIMESTAMP -> list.sortedBy { it.timestamp }
                            else -> list.sortedBy { it.color }
                        }
                    }
                    else -> {
                        when (key) {
                            ORDER_TITLE -> list.sortedByDescending { it.title }
                            ORDER_TIMESTAMP -> list.sortedByDescending { it.timestamp }
                            else -> list.sortedByDescending { it.color }
                        }
                    }
                }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
            refreshNoteList()
        }
    }

    companion object {
        const val ORDER_TITLE = "title"
        const val ORDER_COLOR = "color"
        const val ORDER_TIMESTAMP = "timestamp"

        const val ORDER_ASC = true
        const val ORDER_DESC = false

        const val FILTER_OPEN = View.VISIBLE
        const val FILTER_CLOSE = View.GONE

        const val NOTE_KEY = "note_key"
    }
}

@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory(
    private val repository: NoteRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java))
            return NotesViewModel(repository) as T
        else throw IllegalArgumentException()
    }
}