package com.survivalcoding.noteapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "notes")
data class NoteItem(
  @PrimaryKey(autoGenerate = true) val id: Int? = null,
  val title: String,
  val content: String,
  val timestamp: Long = Date().time,
  val color: Int,
  val isDone: Boolean = false
) : Parcelable
