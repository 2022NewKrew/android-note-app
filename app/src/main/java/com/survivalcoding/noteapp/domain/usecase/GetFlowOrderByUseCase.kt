package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.repository.SelectAllRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetFlowOrderByUseCase<T>(private val repository: SelectAllRepository<T>) {
    operator fun invoke(
        comparatorFlow: Flow<Comparator<T>>,
        isAscFlow: Flow<Boolean>
    ): Flow<List<T>> {
        return combine(repository.selectAll(), comparatorFlow, isAscFlow) { ts, comparator, isAsc ->
            ts.sortedWith(comparator).run { if (!isAsc) reversed() else this}
        }
    }
}