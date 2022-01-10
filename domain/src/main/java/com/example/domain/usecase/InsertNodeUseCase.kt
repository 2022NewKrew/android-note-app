package com.example.domain.usecase

import com.example.domain.entity.Note
import com.example.domain.repository.NoteRepository

class InsertNodeUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        repository.insertNote(note)
    }
}
