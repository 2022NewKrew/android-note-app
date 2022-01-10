package com.survivalcoding.noteapp.domain.model

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Note(
    @PrimaryKey val id: Long? = null,
    val title: String = "",
    val content: String = "",
    val timestamp: Long = Date().time,
    val color: Int = Color.CYAN,
)