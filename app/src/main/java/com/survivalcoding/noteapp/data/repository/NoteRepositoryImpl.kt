package com.survivalcoding.noteapp.data.repository

import com.survivalcoding.noteapp.data.datasource.NoteDao
import com.survivalcoding.noteapp.domain.model.NoteItem
import com.survivalcoding.noteapp.domain.repository.NoteRepository

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {

  override fun getNotes(): List<NoteItem> = noteDao.getAll()

  override suspend fun getNoteById(id: Int): NoteItem? = noteDao.getById(id)

  override suspend fun upsertNote(noteItem: NoteItem) = noteDao.upsert(noteItem)

  override suspend fun deleteNote(noteItem: NoteItem) = noteDao.delete(noteItem)
}
