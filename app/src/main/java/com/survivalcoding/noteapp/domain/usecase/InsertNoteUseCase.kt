package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository

class InsertNoteUseCase(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.insertNote(note)
}