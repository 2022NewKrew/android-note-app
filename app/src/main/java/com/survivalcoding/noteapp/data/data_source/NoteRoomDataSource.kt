package com.survivalcoding.noteapp.data.data_source

import com.survivalcoding.noteapp.domain.model.Note

class NoteRoomDataSource(private val noteDao: NoteDao) : NoteLocalDataSource {

    override suspend fun getNotesOrderByTitleAsc() =
        noteDao.getOrderByTitleAsc().map { convert(it) }

    override suspend fun getNotesOrderByTitleDesc() =
        noteDao.getOrderByTitleDesc().map { convert(it) }

    override suspend fun getNotesOrderByDateAsc() = noteDao.getOrderByTimeAsc().map { convert(it) }

    override suspend fun getNotesOrderByDateDesc() =
        noteDao.getOrderByTimeDesc().map { convert(it) }

    override suspend fun getNotesOrderByColorAsc() =
        noteDao.getOrderByColorAsc().map { convert(it) }

    override suspend fun getNotesOrderByColorDesc() =
        noteDao.getOrderByColorDesc().map { convert(it) }

    override suspend fun insertNote(note: Note) = noteDao.insert(convert(note))

    override suspend fun deleteNote(note: Note) = noteDao.delete(convert(note))

    private fun convert(noteDto: NoteDto) = Note(
        id = noteDto.id,
        title = noteDto.title,
        content = noteDto.content,
        timestamp = noteDto.timestamp,
        color = noteDto.color
    )

    private fun convert(note: Note) = NoteDto(
        id = note.id,
        title = note.title,
        content = note.content,
        timestamp = note.timestamp,
        color = note.color
    )
}