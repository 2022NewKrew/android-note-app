package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.repository.NoteRepository

class GetAllNotesUseCase(private val noteRepository: NoteRepository) {
  operator fun invoke() = noteRepository.getNotes()
}
