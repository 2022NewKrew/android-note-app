package com.survivalcoding.noteapp.presentation.addedit

import androidx.lifecycle.*
import com.survivalcoding.noteapp.domain.model.Color
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.usecase.InsertNoteUseCase
import kotlinx.coroutines.launch

class AddEditViewModel(private val insertNoteUseCase: InsertNoteUseCase) : ViewModel() {
    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note> get() = _note
    private val _color = MutableLiveData(Color.ORANGE)
    val color: LiveData<Color> get() = _color

    fun insertNote(note: Note) = viewModelScope.launch {
        insertNoteUseCase(note)
    }

    fun changeColor(color: Color) {
        _color.value = color
    }
}