package com.survivalcoding.noteapp.domain.repository

import com.survivalcoding.noteapp.domain.model.Note

interface NoteRepository: SelectAllRepository<Note> {
    suspend fun selectById(id: Int): Note?

    suspend fun upsert(note: Note)

    suspend fun delete(note: Note)
}