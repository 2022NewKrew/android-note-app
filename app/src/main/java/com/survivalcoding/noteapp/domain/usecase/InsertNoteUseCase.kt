package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository

class InsertNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) = repository.insertNote(note)
}