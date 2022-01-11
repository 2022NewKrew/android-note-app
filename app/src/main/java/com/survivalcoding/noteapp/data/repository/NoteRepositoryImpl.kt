package com.survivalcoding.noteapp.data.repository

import com.survivalcoding.noteapp.data.data_source.NoteLocalDataSource
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository

class NoteRepositoryImpl(private val noteLocalDataSource: NoteLocalDataSource) : NoteRepository {
    override suspend fun getNotesOrderByTitleAsc() = noteLocalDataSource.getNotesOrderByTitleAsc()

    override suspend fun getNotesOrderByTitleDesc() = noteLocalDataSource.getNotesOrderByTitleDesc()

    override suspend fun getNotesOrderByDateAsc() = noteLocalDataSource.getNotesOrderByDateAsc()

    override suspend fun getNotesOrderByDateDesc() = noteLocalDataSource.getNotesOrderByDateDesc()

    override suspend fun getNotesOrderByColorAsc() = noteLocalDataSource.getNotesOrderByColorAsc()

    override suspend fun getNotesOrderByColorDesc() = noteLocalDataSource.getNotesOrderByColorDesc()

    override suspend fun insertNote(note: Note) = noteLocalDataSource.insertNote(note)

    override suspend fun deleteNote(note: Note) = noteLocalDataSource.deleteNote(note)
}