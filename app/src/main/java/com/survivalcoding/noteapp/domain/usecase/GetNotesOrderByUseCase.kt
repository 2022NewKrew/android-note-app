package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.NoteOrderBy
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class GetNotesOrderByUseCase(private val repository: NoteRepository) {
    @ExperimentalCoroutinesApi
    operator fun invoke(
        noteOrderByFlow: Flow<NoteOrderBy>,
        isAscFlow: Flow<Boolean>
    ): Flow<List<Note>> {
        return combine(noteOrderByFlow, isAscFlow) {noteOrderBy, isAsc ->
            Pair(noteOrderBy, isAsc)
        }.flatMapLatest { (noteOrderBy, isAsc) ->
            repository.selectAllOrderBy(noteOrderBy.columnName, isAsc)
        }
    }
}