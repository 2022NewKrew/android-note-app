package com.survivalcoding.noteapp.data.datasource

import com.survivalcoding.noteapp.domain.model.Note

class NoteRoomDataSource(private val dao: NoteDao): NoteDataSource {
    override suspend fun insertNote(note: Note) = dao.insertNote(note)

    override suspend fun deleteNote(note: Note) = dao.deleteNote(note)

    override suspend fun sortByColorAsc(): List<Note> = dao.sortByColorAsc()

    override suspend fun sortByColorDesc(): List<Note> = dao.sortByColorDesc()

    override suspend fun sortByTitleAsc(): List<Note> = dao.sortByTitleAsc()

    override suspend fun sortByTitleDesc(): List<Note> = dao.sortByTitleDesc()

    override suspend fun sortByTimestampAsc(): List<Note> = dao.sortByTimestampAsc()

    override suspend fun sortByTimestampDesc(): List<Note> = dao.sortByTimestampDesc()
}