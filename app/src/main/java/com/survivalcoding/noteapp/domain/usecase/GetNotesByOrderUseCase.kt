package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.model.Order
import com.survivalcoding.noteapp.domain.repository.NoteRepository

class GetNotesByOrderUseCase(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(order: Order) = noteRepository.getSortedNotes(order)
}