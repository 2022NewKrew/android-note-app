package com.survivalcoding.noteapp.data.data_source

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.Order

class NoteRoomDataSource(private val noteDao: NoteDao) : NoteLocalDataSource {

    override suspend fun getSortedNotes(order: Order): List<Note> {
        return when (order) {
            Order.TITLE_ASC -> noteDao.getOrderByTitleAsc().map { convert(it) }
            Order.TITLE_DESC -> noteDao.getOrderByTitleDesc().map { convert(it) }
            Order.DATE_ASC -> noteDao.getOrderByTimeAsc().map { convert(it) }
            Order.DATE_DESC -> noteDao.getOrderByTimeDesc().map { convert(it) }
            Order.COLOR_ASC -> noteDao.getOrderByColorAsc().map { convert(it) }
            Order.COLOR_DESC -> noteDao.getOrderByColorDesc().map { convert(it) }
        }
    }

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