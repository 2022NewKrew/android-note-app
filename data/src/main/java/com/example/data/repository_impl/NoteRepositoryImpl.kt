package com.example.data.repository_impl

import com.example.data.db.note.NoteDatabase
import com.example.domain.entity.Note
import com.example.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val db: NoteDatabase) : NoteRepository {

    private val dao = db.noteDao()

    override fun getNotes(): Flow<List<Note>> = dao.getAll()

    override fun getNoteById(id: Long): Flow<Note?> = dao.getNoteById(id)

    override suspend fun insertNote(note: Note) {
        dao.insert(note)
    }

    override suspend fun updateNote(note: Note) {
        dao.update(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.delete(note)
    }
}