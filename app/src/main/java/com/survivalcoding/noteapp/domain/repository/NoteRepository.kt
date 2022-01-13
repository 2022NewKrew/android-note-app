package com.survivalcoding.noteapp.domain.repository

import com.survivalcoding.noteapp.domain.model.NoteItem

interface NoteRepository {
  fun getNotes(): List<NoteItem>

  suspend fun getNoteById(id: Int): NoteItem?

  suspend fun upsertNote(noteItem: NoteItem)

  suspend fun deleteNote(noteItem: NoteItem)
}
