package com.survivalcoding.noteapp.presentation.addedit

import androidx.lifecycle.*
import com.survivalcoding.noteapp.domain.model.Color
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.usecase.GetNoteByIdUseCase
import com.survivalcoding.noteapp.domain.usecase.InsertNoteUseCase
import kotlinx.coroutines.launch

class AddEditViewModel(
    private val insertNoteUseCase: InsertNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase
) : ViewModel() {
    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note> get() = _note
    private val _color = MutableLiveData(Color.ORANGE)
    val color: LiveData<Color> get() = _color

    fun insertNote(title: String, content: String, timestamp: Long) = viewModelScope.launch {
        val color = color.value ?: Color.ORANGE
        val note = (note.value?.copy(
            title = title,
            content = content,
            timestamp = timestamp,
            color = color.resId()
        ) ?: Note(null, title, content, timestamp, color.resId()))
        insertNoteUseCase(note)
    }

    fun getNoteById(id: Int) = viewModelScope.launch {
        _note.value = getNoteByIdUseCase.invoke(id)
    }

    fun changeColor(color: Color) {
        _color.value = color
    }
}