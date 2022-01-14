package com.survivalcoding.noteapp.presentation.add_edit_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.domain.model.ColorItem
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

    private val _backgroundColor = MutableStateFlow<List<ColorItem>>(listOf())

    val noteState =
        combine(_title, _content, _color, _backgroundColor) { title, content, color, bc ->
            NoteState(title, content, color, bc)
        }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            AddEditNoteEvent.InsertNote -> viewModelScope.launch {
                insertNoteUseCase(
                    Note(
                        id = _id.value,
                        title = _title.value,
                        content = _content.value,
                        color = _color.value,
                    )
                )
            }
            is AddEditNoteEvent.SetColor -> {
                _color.value = event.color

                val colorList = MutableList(5) { ColorItem(it.toLong(), false) }
                when (event.color) {
                    NoteColor.COLOR_1 -> colorList[0] = colorList[0].copy(isChecked = true)
                    NoteColor.COLOR_2 -> colorList[1] = colorList[1].copy(isChecked = true)
                    NoteColor.COLOR_3 -> colorList[2] = colorList[2].copy(isChecked = true)
                    NoteColor.COLOR_4 -> colorList[3] = colorList[3].copy(isChecked = true)
                    NoteColor.COLOR_5 -> colorList[4] = colorList[4].copy(isChecked = true)
                }
                _backgroundColor.value = colorList
            }
            is AddEditNoteEvent.SetContent -> _content.value = event.content
            is AddEditNoteEvent.SetId -> _id.value = event.id
            is AddEditNoteEvent.SetTitle -> _title.value = event.title
        }
    }
}