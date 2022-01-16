package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.data.repository.NotesInMemoryRepository
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.Order
import com.survivalcoding.noteapp.domain.model.SortBy
import com.survivalcoding.noteapp.domain.repository.NotesRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetSortedNotesUseCaseTest {
    private lateinit var useCase: GetSortedNotesUseCase
    private lateinit var repository: NotesRepository

    @Before
    fun setUp() {
        repository = NotesInMemoryRepository()
        useCase = GetSortedNotesUseCase(repository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun sortTest() = runBlocking {
        repository.insertNote(Note(color = 0))
        repository.insertNote(
            Note(
                title = "7",
                timestamp = System.currentTimeMillis() - 1000L * 60L * 60L * 24L,
                color = 1
            )
        )
        repository.insertNote(
            Note(
                title = "5",
                timestamp = System.currentTimeMillis() - 1000L * 60L * 60L * 24L * 2,
                color = 2
            )
        )
        repository.insertNote(
            Note(
                title = "2",
                timestamp = System.currentTimeMillis() + 1000L * 60L * 60L * 24L,
                color = 3
            )
        )

        repository.insertNote(
            Note(
                title = "3",
                timestamp = System.currentTimeMillis() + 1000L * 60L * 60L * 24L * 2,
                color = 4
            )
        )

        assertEquals(5, repository.getNotes().size)
        assertEquals(2, repository.getNoteById(3)?.color)

        assertEquals(4, useCase(SortBy.BY_TITLE, Order.ASC)[1].id)
        assertEquals(1, useCase(SortBy.BY_DATE, Order.ASC)[2].id)
        assertEquals(5, useCase(SortBy.BY_COLOR, Order.ASC)[4].id)

        assertEquals(4, useCase(SortBy.BY_TITLE, Order.DESC)[3].id)
        assertEquals(2, useCase(SortBy.BY_DATE, Order.DESC)[3].id)
        assertEquals(1, useCase(SortBy.BY_COLOR, Order.DESC)[4].id)
    }
}