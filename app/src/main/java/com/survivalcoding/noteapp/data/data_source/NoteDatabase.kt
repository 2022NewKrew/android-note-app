package com.survivalcoding.noteapp.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteDto::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        const val NOTE_DATABASE_NAME = "note-database"
    }
}