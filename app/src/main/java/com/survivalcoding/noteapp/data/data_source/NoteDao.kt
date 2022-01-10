package com.survivalcoding.noteapp.data.data_source

import androidx.room.*
import com.survivalcoding.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note ORDER BY CASE WHEN :order = 1 THEN :key END ASC, CASE WHEN :order = 0 THEN :key END DESC")
    suspend fun getAllNotes(key: String, order: Boolean): List<Note>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Long): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg notes: Note)

    @Delete
    suspend fun delete(note: Note)
}