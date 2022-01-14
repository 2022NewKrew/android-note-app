package com.survivalcoding.noteapp.domain.model

data class NoteState(
    val title: String = "",
    val content: String = "",
    val color: NoteColor = NoteColor.COLOR_1,
)