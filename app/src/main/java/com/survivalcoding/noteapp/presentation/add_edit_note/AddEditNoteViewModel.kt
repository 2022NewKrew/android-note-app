package com.survivalcoding.noteapp.presentation.add_edit_note

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NotesRepository
import com.survivalcoding.noteapp.presentation.notes.NotesFragment.Companion.MODIFY
import kotlinx.coroutines.launch

class AddEditNoteViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository
) : ViewModel() {
    private val _addEditNote = MutableLiveData(Note(color = 0))
    val addEditNote: LiveData<Note> get() = _addEditNote

    init {
        savedStateHandle.get<Int>(MODIFY)?.let { it ->
            viewModelScope.launch {
                notesRepository.getNoteById(it)?.let {
                    _addEditNote.value = it
                }
            }
        }
    }

    fun getNote(): LiveData<Note> = addEditNote

    fun updateColor(color: Int) {
        _addEditNote.value?.let {
            _addEditNote.value = it.copy(color = color)
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