package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.data.repository.NotesInMemoryRepository
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NotesRepository
import com.survivalcoding.noteapp.presentation.notes.NotesFragment.Companion.BY_COLOR
import com.survivalcoding.noteapp.presentation.notes.NotesFragment.Companion.BY_DATE
import com.survivalcoding.noteapp.presentation.notes.NotesFragment.Companion.BY_TITLE
import com.survivalcoding.noteapp.presentation.notes.NotesFragment.Companion.SORT_ASC
import com.survivalcoding.noteapp.presentation.notes.NotesFragment.Companion.SORT_DESC
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SortNotesUseCaseTest {
    private lateinit var useCase: SortNotesUseCase
    private lateinit var repository: NotesRepository

    @Before
    fun setUp() {
        repository = NotesInMemoryRepository()
        useCase = SortNotesUseCase(repository)
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

        assertEquals(4, useCase(BY_TITLE, SORT_ASC)[1].id)
        assertEquals(1, useCase(BY_DATE, SORT_ASC)[2].id)
        assertEquals(5, useCase(BY_COLOR, SORT_ASC)[4].id)

        assertEquals(4, useCase(BY_TITLE, SORT_DESC)[3].id)
        assertEquals(2, useCase(BY_DATE, SORT_DESC)[3].id)
        assertEquals(1, useCase(BY_COLOR, SORT_DESC)[4].id)
    }
}