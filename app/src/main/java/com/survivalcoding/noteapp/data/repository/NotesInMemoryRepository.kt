package com.survivalcoding.noteapp.data.repository

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NotesRepository

class NotesInMemoryRepository : NotesRepository {
    private var notes = listOf<Note>()
    override suspend fun getNotes(): List<Note> = notes

    override suspend fun getNoteById(id: Int): Note? {
        return notes.find { it.id == id }
    }

    override suspend fun insertNote(note: Note) {
        notes = notes.plus(note)
    }

    override suspend fun deleteNote(note: Note) {
        notes = notes.minus(note)
    }
}