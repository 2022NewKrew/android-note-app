package com.survivalcoding.noteapp.domain.model

import android.view.View

data class UIState(
    val sortKey: SortKey = SortKey.TITLE,
    val sortMode: SortMode = SortMode.ASCENDING,
    val notes: List<Note> = listOf(),
    val visibility: Int = View.GONE,
)