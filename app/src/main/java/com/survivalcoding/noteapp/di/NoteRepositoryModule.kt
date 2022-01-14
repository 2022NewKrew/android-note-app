package com.survivalcoding.noteapp.di

import com.survivalcoding.noteapp.data.datasource.NoteDataSource
import com.survivalcoding.noteapp.data.di.NoteDataSourceModule.MockDataSource
import com.survivalcoding.noteapp.data.repository.NoteRepositoryImpl
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NoteRepositoryModule {
  @Singleton
  @Provides
  fun provideNoteRepository(@MockDataSource dataSource: NoteDataSource): NoteRepository = NoteRepositoryImpl(dataSource)
}
