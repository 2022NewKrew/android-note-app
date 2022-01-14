package com.survivalcoding.noteapp.data.di

import android.content.Context
import androidx.room.Room
import com.survivalcoding.noteapp.data.datasource.database.NoteDao
import com.survivalcoding.noteapp.data.datasource.database.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NoteDatabaseModule {

  @Singleton
  @Provides
  fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase =
    Room.databaseBuilder(context, NoteDatabase::class.java, "note-db").build()

  @Provides
  fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao = noteDatabase.noteDao()
}
