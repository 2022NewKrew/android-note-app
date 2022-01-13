package com.survivalcoding.noteapp

import android.app.Application
import androidx.room.Room
import com.survivalcoding.noteapp.data.data_source.NoteDatabase
import com.survivalcoding.noteapp.data.repository.NoteRepositoryImpl

class App : Application() {
    val noteRepository by lazy {
        NoteRepositoryImpl(
            Room.databaseBuilder(
                applicationContext,
                NoteDatabase::class.java,
                "noteDB"
            ).build().noteDao()
        )
    }
}