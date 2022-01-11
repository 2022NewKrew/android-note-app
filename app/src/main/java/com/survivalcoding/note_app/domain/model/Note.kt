package com.survivalcoding.note_app.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.Exception
import java.util.*

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long = Date().time,
    val color: Int,
    @PrimaryKey val id: Int? = null,
)

class InvalidNoteException(message: String) : Exception(message)