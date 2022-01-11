package com.survivalcoding.noteapp.data.data_source

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
)