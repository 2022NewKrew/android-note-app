package com.survivalcoding.noteapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.lang.Exception
import java.util.*

@Entity
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val title: String = "",
    val content: String = "",
    val timestamp: Long = Date().time,
    val color: NoteColor = NoteColor.COLOR_1,
) : Parcelable

class InvalidNoteException(message: String) : Exception(message)