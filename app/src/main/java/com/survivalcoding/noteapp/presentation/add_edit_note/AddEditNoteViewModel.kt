package com.survivalcoding.noteapp.presentation.add_edit_note

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NotesRepository
import com.survivalcoding.noteapp.presentation.notes.NotesFragment.Companion.MODIFY
import kotlinx.coroutines.runBlocking

class AddEditNoteViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository
) : ViewModel() {
    private var _addEditNote = MutableLiveData(Note(color = 0))
    val addEditNote: LiveData<Note> get() = _addEditNote

    private var note: Note = Note(color = 0)

    init {
        savedStateHandle.get<Int>(MODIFY)?.let { it ->
            runBlocking { // runBlocking을 하지 않으면 onCreatedView가 먼저 실행되는 문제 발견
                notesRepository.getNoteById(it)?.let {
                    note = it
                    _addEditNote.value = note
                }
            }
        }
    }

    fun getNote(): Note = note

    fun changeColor(color: Int) {
        note = note.copy(color = color)
        _addEditNote.value = note
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