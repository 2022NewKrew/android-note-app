package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.repository.NoteRepository

class SortByColorDescUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke() = repository.sortByColorDesc()
}