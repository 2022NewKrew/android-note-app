package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.SortFactor
import com.survivalcoding.noteapp.domain.model.SortType

class GetNotesUseCase(
    private val sortByColorAscUseCase: SortByColorAscUseCase,
    private val sortByColorDescUseCase: SortByColorDescUseCase,
    private val sortByTimestampAscUseCase: SortByTimestampAscUseCase,
    private val sortByTimestampDescUseCase: SortByTimestampDescUseCase,
    private val sortByTitleAscUseCase: SortByTitleAscUseCase,
    private val sortByTitleDescUseCase: SortByTitleDescUseCase,
) {
    suspend operator fun invoke(sortFactor: SortFactor, sortType: SortType): List<Note> {

        when (sortFactor) {
            // color 기준 정렬
            SortFactor.COLOR -> {
                return if (sortType == SortType.ASC) sortByColorAscUseCase()
                else sortByColorDescUseCase()
            }
            // title 기준 정렬
            SortFactor.TITLE -> {
                return if (sortType == SortType.ASC) sortByTitleAscUseCase()
                else sortByTitleDescUseCase()
            }
            // timestamp 기준 정렬
            SortFactor.TIMESTAMP -> {
                return if (sortType == SortType.ASC) sortByTimestampAscUseCase()
                else sortByTimestampDescUseCase()
            }
        }
    }
}