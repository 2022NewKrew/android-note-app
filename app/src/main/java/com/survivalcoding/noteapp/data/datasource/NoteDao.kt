package com.survivalcoding.noteapp.data.datasource

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.survivalcoding.noteapp.domain.model.Note

interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note WHERE color LIKE :color")
    suspend fun getNotesByColor(color: Int): List<Note>

    @Query("SELECT * FROM note ORDER BY color ASC")
    suspend fun sortByColorAsc(): List<Note>

    @Query("SELECT * FROM note ORDER BY color DESC")
    suspend fun sortByColorDesc(): List<Note>

    @Query("SELECT * FROM note ORDER BY title ASC")
    suspend fun sortByTitleAsc(): List<Note>

    @Query("SELECT * FROM note ORDER BY title DESC")
    suspend fun sortByTitleDesc(): List<Note>

    @Query("SELECT * FROM note ORDER BY timestamp ASC")
    suspend fun sortByTimestampAsc(): List<Note>

    @Query("SELECT * FROM note ORDER BY timestamp DESC")
    suspend fun sortByTimestampDesc(): List<Note>
}