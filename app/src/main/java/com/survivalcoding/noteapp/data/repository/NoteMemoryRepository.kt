package com.survivalcoding.noteapp.data.repository

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class NoteMemoryRepository : NoteRepository {
    private var nextId = 1

    private val _notes: MutableStateFlow<List<Note>> = MutableStateFlow(listOf())

    override suspend fun selectById(id: Int): Note? {
        _notes.value.run {
            val index = indexOfFirst { it.id == id }
            return if (index != -1) {
                this[index]
            } else {null}
        }
    }

    override fun selectAll(): Flow<List<Note>> {
        return _notes
    }

    override suspend fun upsert(note: Note) {
        _notes.value = _notes.value.toMutableList().apply {
            val index = indexOfFirst { it.id == note.id }
            if (index != -1) {
                this[index] = note.copy()
            } else {
                add(note.copy(id = nextId))
                nextId += 1
            }
        }.toList()
    }

    override suspend fun delete(note: Note) {
        _notes.value = _notes.value.toMutableList().apply {
            val index = indexOfFirst { it.id == note.id }
            if (index != -1) {
                removeAt(index)
            }
        }.toList()
    }
}