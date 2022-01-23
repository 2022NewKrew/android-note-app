package com.survivalcoding.noteapp.presentation.note

import android.icu.text.CaseMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NoteViewModel(private val noteRepository: NoteRepository): ViewModel() {

    private val _note = MutableStateFlow(Note())
    private val _saved = MutableStateFlow(false)
    val uiState: Flow<NoteUiState> = combine(_note, _saved) { n, s -> NoteUiState(n, s) }

    fun setNote(note: Note) {
        _note.value = note
    }

    fun setTitle(title: String) {
        _note.value = _note.value.copy(title = title)
    }

    fun setContent(content: String) {
        _note.value = _note.value.copy(content = content)
    }

    fun setColor(color: Int) {
        _note.value = _note.value.copy(color = color)
    }

    fun saveNote() {
        viewModelScope.launch {
            noteRepository.upsert(_note.value)
            _saved.value = true
        }
    }
}