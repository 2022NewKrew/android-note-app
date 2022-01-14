package com.survivalcoding.noteapp.data.datasource

import com.survivalcoding.noteapp.domain.model.NoteItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Singleton

@Singleton
class NoteMockDataSource : NoteDataSource {
  private val _notes = (1..30).map {
    NoteItem(
      id = it,
      title = "title $it",
      content = "content of task # $it",
      color = 80 + 80 * 256 + 80 * 256 * 256
    )
  }.toMutableList()

  override fun getNotes(): Flow<List<NoteItem>> = flowOf(_notes)

  override suspend fun getNoteById(id: Int): NoteItem? = _notes.first { it.id == id }

  override suspend fun upsertNote(noteItem: NoteItem) {
    if (noteItem.id == null) {
      val newId = _notes.maxOf { it.id ?: 1 }
      _notes.add(noteItem.copy(id = newId))
    } else {
      _notes.add(noteItem)
    }
  }

  override suspend fun deleteNote(noteItem: NoteItem) {
    _notes.remove(noteItem)
  }
}
