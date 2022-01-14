package com.survivalcoding.noteapp.domain.repository

import com.survivalcoding.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun selectAll(): Flow<List<Note>>

    suspend fun selectById(id: Int): Note?

    fun selectAllOrderBy(columnName: String, isAsc: Boolean): Flow<List<Note>>

    suspend fun upsert(note: Note)

    suspend fun delete(note: Note)
}