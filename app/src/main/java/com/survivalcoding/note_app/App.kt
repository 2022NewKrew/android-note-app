package com.survivalcoding.note_app

import android.app.Application
import androidx.room.Room
import com.survivalcoding.note_app.data.data_source.NoteDatabase
import com.survivalcoding.note_app.data.repository.NoteRepositoryImpl
import com.survivalcoding.note_app.domain.repository.NoteRepository
import com.survivalcoding.note_app.domain.use_case.AddNoteUseCase
import com.survivalcoding.note_app.domain.use_case.DeleteNoteUseCase
import com.survivalcoding.note_app.domain.use_case.GetNotesUseCase
import com.survivalcoding.note_app.domain.use_case.NoteUseCases

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

    val noteUseCases: NoteUseCases by lazy {
        NoteUseCases(
            getNotes = GetNotesUseCase(repository),
            deleteNote = DeleteNoteUseCase(repository),
            addNote = AddNoteUseCase(repository),
        )
    }
}