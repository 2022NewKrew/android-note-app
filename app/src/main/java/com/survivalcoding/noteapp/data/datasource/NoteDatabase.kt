package com.survivalcoding.noteapp.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.survivalcoding.noteapp.domain.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}