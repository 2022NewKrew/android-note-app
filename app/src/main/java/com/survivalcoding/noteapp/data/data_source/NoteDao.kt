package com.survivalcoding.noteapp.data.data_source

import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM notedto ORDER BY title ASC")
    suspend fun getOrderByTitleAsc(): List<NoteDto>

    @Query("SELECT * FROM notedto ORDER BY title DESC")
    suspend fun getOrderByTitleDesc(): List<NoteDto>

    @Query("SELECT * FROM notedto ORDER BY timestamp ASC")
    suspend fun getOrderByTimeAsc(): List<NoteDto>

    @Query("SELECT * FROM notedto ORDER BY timestamp DESC")
    suspend fun getOrderByTimeDesc(): List<NoteDto>

    @Query("SELECT * FROM notedto ORDER BY color ASC")
    suspend fun getOrderByColorAsc(): List<NoteDto>

    @Query("SELECT * FROM notedto ORDER BY color DESC")
    suspend fun getOrderByColorDesc(): List<NoteDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteDto: NoteDto)

    @Delete
    suspend fun delete(noteDto: NoteDto)
}