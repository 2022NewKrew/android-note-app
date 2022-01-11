package com.survivalcoding.noteapp.data.repository

import com.survivalcoding.noteapp.data.datasource.NoteDao
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository

class NoteRepositoryImpl(private val dao: NoteDao) : NoteRepository {
    override suspend fun insertNote(note: Note) = dao.insertNote(note)

    override suspend fun deleteNote(note: Note) = dao.deleteNote(note)

    override suspend fun getNotesByColor(color: Int): List<Note> = dao.getNotesByColor(color)

    override suspend fun sortByColorAsc(): List<Note> = dao.sortByColorAsc()

    override suspend fun sortByColorDesc(): List<Note> = dao.sortByColorDesc()

    override suspend fun sortByTitleAsc(): List<Note> = dao.sortByTitleAsc()

    override suspend fun sortByTitleDesc(): List<Note> = dao.sortByTitleDesc()

    override suspend fun sortByTimestampAsc(): List<Note> = dao.sortByTimestampAsc()

    override suspend fun sortByTimestampDesc(): List<Note> = dao.sortByTimestampDesc()
}