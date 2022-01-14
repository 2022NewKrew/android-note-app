package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.SortMode
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetSortedNotesUseCase(
    private val repository: NoteRepository,
) {
    operator fun invoke(
        sortKey: Flow<Comparator<Note>>,
        sortMode: Flow<SortMode>
    ): Flow<List<Note>> {
        return combine(repository.getNotes(), sortKey, sortMode) { list, comparator, mode ->
            list.sortedWith(comparator)
                .run { if (mode == SortMode.DESCENDING) reversed() else this }
        }
    }
}