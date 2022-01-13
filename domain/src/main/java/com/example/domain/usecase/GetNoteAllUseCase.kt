package com.example.domain.usecase

import com.example.domain.repository.NoteRepository

class GetNoteAllUseCase(private val repository: NoteRepository) {
    operator fun invoke() = repository.getNotes()
}