package com.survivalcoding.noteapp.data.data_source

import androidx.room.*
import com.survivalcoding.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Long): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg notes: Note)

    @Delete
    suspend fun delete(note: Note)
}