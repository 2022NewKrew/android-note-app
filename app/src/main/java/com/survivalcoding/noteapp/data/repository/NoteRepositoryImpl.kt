package com.survivalcoding.noteapp.data.repository

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl : NoteRepository {
    override suspend fun getNotesOrderByTitle(): List<Note> {
        TODO("Not yet implemented")
    }

    override suspend fun getNotesOrderByDate(): List<Note> {
        TODO("Not yet implemented")
    }

    override suspend fun getNotesOrderByColor(): List<Note> {
        TODO("Not yet implemented")
    }

    override suspend fun getNoteById(id: Int): Note? {
        TODO("Not yet implemented")
    }

    override suspend fun insertNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(note: Note) {
        TODO("Not yet implemented")
    }
}