package com.survivalcoding.noteapp.domain.repository

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.Order

interface NoteRepository {
    suspend fun getSortedNotes(order: Order): List<Note>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}