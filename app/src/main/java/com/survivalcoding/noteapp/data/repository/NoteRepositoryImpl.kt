package com.survivalcoding.noteapp.data.repository

import com.survivalcoding.noteapp.data.datasource.NoteDataSource
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository

class NoteRepositoryImpl(private val dataSource: NoteDataSource) : NoteRepository {
    override suspend fun getNoteById(id: Int): Note = dataSource.getNoteById(id)

    override suspend fun insertNote(note: Note) = dataSource.insertNote(note)

    override suspend fun deleteNote(note: Note) = dataSource.deleteNote(note)

    override suspend fun sortByColorAsc(): List<Note> = dataSource.sortByColorAsc()

    override suspend fun sortByColorDesc(): List<Note> = dataSource.sortByColorDesc()

    override suspend fun sortByTitleAsc(): List<Note> = dataSource.sortByTitleAsc()

    override suspend fun sortByTitleDesc(): List<Note> = dataSource.sortByTitleDesc()

    override suspend fun sortByTimestampAsc(): List<Note> = dataSource.sortByTimestampAsc()

    override suspend fun sortByTimestampDesc(): List<Note> = dataSource.sortByTimestampDesc()
}