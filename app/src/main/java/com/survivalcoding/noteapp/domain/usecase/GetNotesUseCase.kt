package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase(
    private val repository: NoteRepository,
) {
    operator fun invoke(): Flow<List<Note>> {
        return repository.getNotes()
    }
}