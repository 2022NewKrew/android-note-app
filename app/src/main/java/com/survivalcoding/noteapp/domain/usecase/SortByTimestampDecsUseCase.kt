package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.repository.NoteRepository

class SortByTimestampDecsUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke() = repository.sortByTimestampDesc()
}