package com.survivalcoding.noteapp

import android.app.Application
import androidx.room.Room
import com.example.data.db.note.NoteDatabase
import com.example.data.repository_impl.NoteRepositoryImpl
import com.example.domain.repository.NoteRepository

class App : Application() {

    val repository: NoteRepository by lazy {
        NoteRepositoryImpl(
            Room.databaseBuilder(
                baseContext,
                NoteDatabase::class.java, NoteDatabase.DATABASE_NAME
            ).build()
        )

    }

    override fun onCreate() {
        super.onCreate()
    }
}