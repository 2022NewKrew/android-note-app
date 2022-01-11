package com.survivalcoding.noteapp.domain.repository

import com.survivalcoding.noteapp.domain.model.Note

interface NoteRepository {
    suspend fun getNotesOrderByTitleAsc(): List<Note>

    suspend fun getNotesOrderByTitleDesc(): List<Note>

    suspend fun getNotesOrderByDateAsc(): List<Note>

    suspend fun getNotesOrderByDateDesc(): List<Note>

    suspend fun getNotesOrderByColorAsc(): List<Note>

    suspend fun getNotesOrderByColorDesc(): List<Note>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}