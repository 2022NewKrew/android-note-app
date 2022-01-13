package com.survivalcoding.noteapp.data.repository

import com.survivalcoding.noteapp.data.datasource.NoteDao
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRoomRepository(private val noteDao: NoteDao) : NoteRepository {

    override fun selectAll(): Flow<List<Note>> {
        return noteDao.selectAll()
    }

    override suspend fun selectById(id: Int): Note? {
        return noteDao.selectById(id)
    }

    override suspend fun upsert(note: Note) {
        noteDao.upsert(note)
    }

    override suspend fun delete(note: Note) {
        noteDao.delete(note)
    }
}