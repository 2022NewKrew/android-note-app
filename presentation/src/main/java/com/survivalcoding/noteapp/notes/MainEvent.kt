package com.survivalcoding.noteapp.notes

import com.example.domain.entity.Note

sealed class MainEvent {
    data class SwipeDeleteEvent(val position: Int, val note: Note) : MainEvent()
    data class SortingEvent(val sorted_with: String, val ascOrDesc: String) : MainEvent()
}
