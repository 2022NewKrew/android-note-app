package com.example.data.db.note

import androidx.room.Database
import com.example.domain.entity.Note


@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase {

    companion object {
        const val DATABASE_NAME = "note_database"
    }

    abstract fun noteDao(): NoteDao
}