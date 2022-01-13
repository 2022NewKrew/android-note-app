package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.repository.NoteRepository

class SortByTitleDescUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke() = repository.sortByTitleDesc()
}