package com.survivalcoding.noteapp.presentation.add_edit_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.NoteColor
import com.survivalcoding.noteapp.domain.model.NoteState
import com.survivalcoding.noteapp.domain.usecase.InsertNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class AddEditNoteViewModel(
    private val insertNoteUseCase: InsertNoteUseCase
) : ViewModel() {
    private val _id = MutableStateFlow<Long?>(null)
    private val _title = MutableStateFlow("")
    private val _content = MutableStateFlow("")
    private val _color = MutableStateFlow(NoteColor.COLOR_1)

    val noteState = combine(_title, _content, _color) { title, content, color ->
        NoteState(title, content, color)
    }

    fun setId(id: Long?) {
        _id.value = id
    }

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setContent(content: String) {
        _content.value = content
    }

    fun setColor(color: NoteColor) {
        _color.value = color
    }

    fun insert() {
        viewModelScope.launch {
            insertNoteUseCase(
                Note(
                    id = _id.value,
                    title = _title.value,
                    content = _content.value,
                    color = _color.value,
                )
            )
        }
    }
}