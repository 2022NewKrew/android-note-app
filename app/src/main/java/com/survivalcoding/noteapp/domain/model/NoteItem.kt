package com.survivalcoding.noteapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "notes")
data class NoteItem(
  @PrimaryKey val id: Int? = null,
  val title: String,
  val content: String,
  val timestamp: Long = Date().time,
  val color: Int,
)
