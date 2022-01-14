package com.survivalcoding.noteapp.data.datasource.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.survivalcoding.noteapp.domain.model.NoteItem
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
  @Query("SELECT * FROM notes")
  fun getAll(): Flow<List<NoteItem>>

  @Query("SELECT * FROM notes WHERE id=:id LIMIT 1")
  fun getById(id: Int): NoteItem?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun upsert(item: NoteItem)

  @Delete
  fun delete(item: NoteItem)
}
