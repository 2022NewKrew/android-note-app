package com.survivalcoding.noteapp.presentation.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepository: NoteRepository): ViewModel() {

    private val _note = MutableStateFlow<Note?>(null)
    val note: Flow<Note?> = _note

    fun getNoteById(id: Int) {
        viewModelScope.launch {
            _note.value = noteRepository.selectById(id)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteRepository.upsert(note)
        }
    }
}