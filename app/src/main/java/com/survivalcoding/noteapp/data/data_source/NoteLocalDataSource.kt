package com.survivalcoding.noteapp.data.data_source

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.Order

interface NoteLocalDataSource {
    suspend fun getSortedNotes(order: Order): List<Note>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}