package com.survivalcoding.noteapp.presentation.add_edit_note

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NotesRepository
import kotlinx.coroutines.launch

class AddEditNoteViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository
) : ViewModel() {
    var note: Note = Note(color = 0)
    private var _addEditNote = MutableLiveData(note)
    val addEditNote: LiveData<Note> = _addEditNote

    init {
        viewModelScope.launch {
            savedStateHandle.get<Int>("id")?.let { it ->
                notesRepository.getNoteById(it)?.let {
                    note = it
                    _addEditNote.value = note
                }
            }
        }
    }

    fun changeColor(color: Int) {
        note = note.copy(color = color)
        _addEditNote.value = note
    }

    fun updateNote(title: String, content: String) {
        viewModelScope.launch {
            notesRepository.insertNote(note.copy(title = title, content = content))
        }
    }
}

class AddEditNoteViewModelFactory(
    private val notesRepository: NotesRepository, owner: SavedStateRegistryOwner,
    defaultArgs: Bundle?
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return AddEditNoteViewModel(handle, notesRepository) as T
    }
}