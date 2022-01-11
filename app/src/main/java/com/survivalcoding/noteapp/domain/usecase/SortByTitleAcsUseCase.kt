package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.repository.NoteRepository

class SortByTitleAcsUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke() = repository.sortByTitleAsc()
}