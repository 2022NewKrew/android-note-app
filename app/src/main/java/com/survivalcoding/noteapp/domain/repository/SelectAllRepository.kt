package com.survivalcoding.noteapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface SelectAllRepository<T> {
    fun selectAll(): Flow<List<T>>
}