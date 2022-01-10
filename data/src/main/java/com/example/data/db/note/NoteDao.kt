package com.example.data.db.note

import androidx.room.*
import com.example.domain.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM NOTE_TB")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM NOTE_TB WHERE :noteId")
    fun getNoteById(noteId: Long): Flow<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(note: Note)

    @Delete
    fun delete(note: Note)


}
