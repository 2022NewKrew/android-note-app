package com.survivalcoding.noteapp.presentation.notes

import androidx.lifecycle.*
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NotesViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(listOf())
    val notes: LiveData<List<Note>> = _notes.asLiveData()

    var key = ORDER_TITLE
    var mode = ORDER_ASC

    init {
        viewModelScope.launch {
            _notes.value = noteRepository.getNotes(key, mode)
        }
    }

    fun sortNotes() {
        viewModelScope.launch {
            val tmpNotes = noteRepository.getNotes(key, mode)
            _notes.value = tmpNotes
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }

    companion object {
        const val ORDER_TITLE = "title"
        const val ORDER_COLOR = "color"
        const val ORDER_TIMESTAMP = "timestamp"

        const val ORDER_ASC = true
        const val ORDER_DESC = false
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