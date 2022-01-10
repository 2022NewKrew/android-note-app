package com.survivalcoding.noteapp.data.data_source

import androidx.room.*
import com.survivalcoding.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id Like (:id)")
    suspend fun loadById(id: Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg note: Note)

    @Delete
    suspend fun delete(note: Note)
}