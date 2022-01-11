package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.repository.NoteRepository

class GetNotesByColorUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(color: Int) = repository.getNotesByColor(color)
}