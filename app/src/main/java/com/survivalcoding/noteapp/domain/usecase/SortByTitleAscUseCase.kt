package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.repository.NoteRepository

class SortByTitleAscUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke() = repository.sortByTitleAsc()
}