package com.survivalcoding.noteapp

import android.app.Application
import androidx.room.Room
import com.survivalcoding.noteapp.data.data_source.NoteDao
import com.survivalcoding.noteapp.data.data_source.NoteDatabase
import com.survivalcoding.noteapp.data.data_source.NoteLocalDataSource
import com.survivalcoding.noteapp.data.data_source.NoteRoomDataSource
import com.survivalcoding.noteapp.data.repository.NoteRepositoryImpl
import com.survivalcoding.noteapp.domain.repository.NoteRepository

class App : Application() {
    val noteRepository: NoteRepository by lazy {
        NoteRepositoryImpl(noteLocalDataSource)
    }

    private val noteLocalDataSource: NoteLocalDataSource by lazy {
        NoteRoomDataSource(noteDao)
    }

    private val noteDao: NoteDao by lazy {
        Room.databaseBuilder(this, NoteDatabase::class.java, NoteDatabase.NOTE_DATABASE_NAME)
            .build().noteDao()
    }
}