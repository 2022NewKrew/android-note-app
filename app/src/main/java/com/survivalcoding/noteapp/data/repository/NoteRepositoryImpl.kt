package com.survivalcoding.noteapp.data.repository

import com.survivalcoding.noteapp.data.data_source.NoteLocalDataSource
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.Order
import com.survivalcoding.noteapp.domain.repository.NoteRepository

class NoteRepositoryImpl(private val noteLocalDataSource: NoteLocalDataSource) : NoteRepository {
    override suspend fun getSortedNotes(order: Order) = noteLocalDataSource.getSortedNotes(order)

    override suspend fun insertNote(note: Note) = noteLocalDataSource.insertNote(note)

    override suspend fun deleteNote(note: Note) = noteLocalDataSource.deleteNote(note)
}