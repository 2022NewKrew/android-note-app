package com.survivalcoding.noteapp.data.repository

import android.graphics.Color
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository

class NoteRepositoryImpl : NoteRepository {
    override suspend fun getNotesOrderByTitleAsc(): List<Note> = listOf()

    override suspend fun getNotesOrderByTitleDesc(): List<Note> = listOf()

    override suspend fun getNotesOrderByDateAsc(): List<Note> = listOf()

    override suspend fun getNotesOrderByDateDesc(): List<Note> = listOf()

    override suspend fun getNotesOrderByColorAsc(): List<Note> = listOf()

    override suspend fun getNotesOrderByColorDesc(): List<Note> = listOf()

    override suspend fun getNoteById(id: Int): Note? = Note(title = "", content = "", color = Color.YELLOW)

    override suspend fun insertNote(note: Note) {}

    override suspend fun deleteNote(note: Note) {}
}