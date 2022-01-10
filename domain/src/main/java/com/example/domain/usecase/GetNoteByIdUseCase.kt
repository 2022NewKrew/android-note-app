package com.example.domain.usecase

import com.example.domain.repository.NoteRepository

class GetNoteByIdUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(noteId: Long) = repository.getNoteById(noteId)
}