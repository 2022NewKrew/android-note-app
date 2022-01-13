package com.example.domain.usecase

import com.example.domain.entity.Note
import com.example.domain.repository.NoteRepository

class DeleteNodeUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}
