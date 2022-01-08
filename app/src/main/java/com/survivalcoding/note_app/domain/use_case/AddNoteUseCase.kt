package com.survivalcoding.note_app.domain.use_case

import com.survivalcoding.note_app.domain.model.InvalidNoteException
import com.survivalcoding.note_app.domain.model.Note
import com.survivalcoding.note_app.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("제목을 입력해 주세요")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("내용을 입력해 주세요")
        }
        repository.insertNote(note)
    }
}