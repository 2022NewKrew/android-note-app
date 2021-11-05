package com.survivalcoding.core

import android.app.Application
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.room.Room
import com.survivalcoding.note_app.data.data_source.NoteDatabase
import com.survivalcoding.note_app.data.repository.NoteRepositoryImpl
import com.survivalcoding.note_app.domain.repository.NoteRepository

class App : Application() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java, "notes-db"
        ).build()
    }

    val repository: NoteRepository by lazy {
        NoteRepositoryImpl(db.noteDao)
    }
}