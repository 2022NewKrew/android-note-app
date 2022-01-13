package com.survivalcoding.noteapp.domain.model

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val color: Int = Color.TRANSPARENT
)