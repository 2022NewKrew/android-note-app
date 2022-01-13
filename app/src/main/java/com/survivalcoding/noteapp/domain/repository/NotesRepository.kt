package com.survivalcoding.noteapp.domain.repository

import com.survivalcoding.noteapp.domain.model.Note

interface NotesRepository {
    suspend fun getNotes(): List<Note>
    suspend fun getNoteById(id: Int): Note?
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
}