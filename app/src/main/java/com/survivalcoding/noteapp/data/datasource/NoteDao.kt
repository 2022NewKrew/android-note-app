package com.survivalcoding.noteapp.data.datasource

import androidx.room.*
import com.survivalcoding.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note")
    fun selectAll(): Flow<List<Note>>

    @Query("SELECT * FROM Note WHERE id == :id")
    suspend fun selectById(id: Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(vararg note: Note)

    @Delete
    suspend fun delete(vararg note: Note)
}