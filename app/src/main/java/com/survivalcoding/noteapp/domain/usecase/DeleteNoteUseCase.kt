package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.model.NoteItem
import com.survivalcoding.noteapp.domain.repository.NoteRepository

class DeleteNoteUseCase(private val noteRepository: NoteRepository) {
  suspend operator fun invoke(noteItem: NoteItem) = noteRepository.deleteNote(noteItem)
}
