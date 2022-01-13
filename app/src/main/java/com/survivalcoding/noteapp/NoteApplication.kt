package com.survivalcoding.noteapp

import android.app.Application
import androidx.room.Room
import com.survivalcoding.noteapp.data.datasource.NoteDatabase
import com.survivalcoding.noteapp.data.repository.NoteRoomRepository
import com.survivalcoding.noteapp.domain.model.Note
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class NoteApplication : Application() {

    private val noteDatabase by lazy {
        Room.databaseBuilder(this, NoteDatabase::class.java, "note_db").build()
    }

    val noteRepository by lazy {
        NoteRoomRepository(noteDatabase.noteDao())
    }
}