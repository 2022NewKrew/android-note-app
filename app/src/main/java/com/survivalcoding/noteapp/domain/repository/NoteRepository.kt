package com.survivalcoding.noteapp.domain.repository

import com.survivalcoding.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun getNotes(): List<Note>

    suspend fun getNoteById(id: Long): Note?

    suspend fun insertNote(vararg notes: Note)

    suspend fun deleteNote(note: Note)
}