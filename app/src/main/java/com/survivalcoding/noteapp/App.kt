package com.survivalcoding.noteapp

import android.app.Application
import androidx.room.Room
import com.survivalcoding.noteapp.data.data_source.NoteDatabase
import com.survivalcoding.noteapp.data.repository.NotesRepositoryImpl
import com.survivalcoding.noteapp.domain.repository.NotesRepository

class App : Application() {
    val notesRepository: NotesRepository by lazy {
        NotesRepositoryImpl(
            Room.databaseBuilder(
                this,
                NoteDatabase::class.java,
                "database"
            ).build().noteDao()
        )
    }
}