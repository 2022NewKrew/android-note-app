package com.survivalcoding.noteapp.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.survivalcoding.noteapp.domain.model.NoteItem

@Dao
interface NoteDao {
  @Query("SELECT * FROM notes")
  fun getAll(): List<NoteItem>

  @Query("SELECT * FROM notes WHERE id=:id LIMIT 1")
  fun getById(id: Int): NoteItem?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun upsert(item: NoteItem)

  @Delete
  fun delete(item: NoteItem)
}
