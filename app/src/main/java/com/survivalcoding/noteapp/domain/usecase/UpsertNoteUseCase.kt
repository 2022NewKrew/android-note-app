package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.model.NoteItem
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import javax.inject.Inject

class UpsertNoteUseCase @Inject constructor(private val noteRepository: NoteRepository) {
  suspend operator fun invoke(noteItem: NoteItem) = noteRepository.upsertNote(noteItem)
}
