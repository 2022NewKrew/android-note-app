package com.survivalcoding.noteapp.data.repository

import com.survivalcoding.noteapp.data.datasource.NoteDataSource
import com.survivalcoding.noteapp.domain.model.NoteItem
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepositoryImpl @Inject constructor(private val noteDataSource: NoteDataSource) :
  NoteRepository {

  override fun getNotes(): Flow<List<NoteItem>> = noteDataSource.getNotes()

  override suspend fun getNoteById(id: Int): NoteItem? = noteDataSource.getNoteById(id)

  override suspend fun upsertNote(noteItem: NoteItem) = noteDataSource.upsertNote(noteItem)

  override suspend fun deleteNote(noteItem: NoteItem) = noteDataSource.deleteNote(noteItem)
}
