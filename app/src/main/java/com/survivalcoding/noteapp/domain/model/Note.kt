package com.survivalcoding.noteapp.domain.model

import android.graphics.Color
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.survivalcoding.noteapp.presentation.add_edit_note.AddEditNoteFragment
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity
@Parcelize
data class Note(
    @PrimaryKey val id: Long? = null,
    val title: String = "",
    val content: String = "",
    val timestamp: Long = Date().time,
    val color: String = AddEditNoteFragment.COLOR_1,
) : Parcelable