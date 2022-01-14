package com.survivalcoding.noteapp.data.di

import com.survivalcoding.noteapp.data.datasource.NoteDataSource
import com.survivalcoding.noteapp.data.datasource.NoteMockDataSource
import com.survivalcoding.noteapp.data.datasource.NoteRoomDataSource
import com.survivalcoding.noteapp.data.datasource.database.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME

@InstallIn(SingletonComponent::class)
@Module
object NoteDataSourceModule {
  @Qualifier
  @Retention(RUNTIME)
  annotation class MockDataSource

  @Qualifier
  @Retention(RUNTIME)
  annotation class RoomDataSource

  @Singleton
  @Provides
  @MockDataSource
  fun provideNoteMockDataSource(): NoteDataSource = NoteMockDataSource()

  @Singleton
  @Provides
  @RoomDataSource
  fun provideNoteRoomDataSource(noteDao: NoteDao): NoteDataSource = NoteRoomDataSource(noteDao)
}
