package com.survivalcoding.noteapp.data.repository

import com.survivalcoding.noteapp.data.data_source.NoteDao
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {
    override suspend fun getNotes(key: String, mode: Boolean): List<Note> {
        return noteDao.getAllNotes(key, mode)
    }

    override suspend fun getNoteById(id: Long): Note? {
        return noteDao.getNoteById(id)
    }

    override suspend fun insertNote(vararg notes: Note) {
        noteDao.insert(*notes)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.delete(note)
    }
}