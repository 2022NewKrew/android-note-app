package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.Order
import com.survivalcoding.noteapp.domain.repository.NoteRepository

class GetNotesByOrderUseCase(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(order: Order): List<Note> {
        return when (order) {
            Order.TITLE_ASC -> noteRepository.getNotesOrderByTitleAsc()
            Order.TITLE_DESC -> noteRepository.getNotesOrderByTitleDesc()
            Order.DATE_ASC -> noteRepository.getNotesOrderByDateAsc()
            Order.DATE_DESC -> noteRepository.getNotesOrderByDateDesc()
            Order.COLOR_ASC -> noteRepository.getNotesOrderByColorAsc()
            Order.COLOR_DSC -> noteRepository.getNotesOrderByColorDesc()
        }
    }
}