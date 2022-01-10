package com.survivalcoding.noteapp.data.repository

import com.survivalcoding.noteapp.data.data_source.NoteDao
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(private val noteDao: NoteDao) : NotesRepository {
    override fun getNotes(): Flow<List<Note>> = noteDao.getAll()
    override suspend fun getNoteById(id: Int): Note? = noteDao.loadById(id)
    override suspend fun insertNote(note: Note) = noteDao.insert(note)
    override suspend fun deleteNote(note: Note) = noteDao.delete(note)
}