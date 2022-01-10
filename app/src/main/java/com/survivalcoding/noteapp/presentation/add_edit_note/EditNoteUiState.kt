package com.survivalcoding.noteapp.presentation.add_edit_note

import android.text.Editable
import com.survivalcoding.noteapp.domain.model.Color

data class EditNoteUiState(
    val title: Editable = Editable.Factory().newEditable(""),
    val content: Editable = Editable.Factory().newEditable(""),
    val color: Color
)