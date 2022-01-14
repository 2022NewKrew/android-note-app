package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.model.InvalidNoteException
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository

class InsertNoteUseCase(
    private val repository: NoteRepository,
) {
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("제목을 입력해 주세요.")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("내용을 입력해 주세요.")
        }
        repository.insertNote(note)
    }
}