package com.survivalcoding.noteapp.data.datasource

import com.survivalcoding.noteapp.domain.model.Note

class NoteInMemoryDataSource : NoteDataSource {

    private var notes: MutableList<Note> = mutableListOf()

    override suspend fun insertNote(note: Note) {
        notes.add(note.copy(id = notes.size + 1))
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }

    override suspend fun sortByColorAsc(): List<Note> = notes.toList()

    override suspend fun sortByColorDesc(): List<Note> {
        TODO("Not yet implemented")
    }

    override suspend fun sortByTitleAsc(): List<Note> {
        TODO("Not yet implemented")
    }

    override suspend fun sortByTitleDesc(): List<Note> {
        TODO("Not yet implemented")
    }

    override suspend fun sortByTimestampAsc(): List<Note> {
        TODO("Not yet implemented")
    }

    override suspend fun sortByTimestampDesc(): List<Note> {
        TODO("Not yet implemented")
    }

}