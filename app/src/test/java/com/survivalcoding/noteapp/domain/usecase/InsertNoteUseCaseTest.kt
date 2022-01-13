package com.survivalcoding.noteapp.domain.usecase

import android.graphics.Color
import com.survivalcoding.noteapp.data.datasource.NoteInMemoryDataSource
import com.survivalcoding.noteapp.data.repository.NoteRepositoryImpl
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.Instant
import java.util.*

class InsertNoteUseCaseTest {

    private lateinit var useCase: InsertNoteUseCase
    private lateinit var noteRepository: NoteRepository

    @Before
    fun setUp() {
        noteRepository = NoteRepositoryImpl(NoteInMemoryDataSource())
        useCase = InsertNoteUseCase(repository = noteRepository)
    }

    @Test
    fun simpleTest() {
        runBlocking {
            useCase(Note(id = 1, title = "test1", content = "content1", timestamp = Date.from(Instant.now()).time, color = Color.RED))
            assertEquals("test1", noteRepository.sortByColorAsc().last().title)

            useCase(Note(id = 2, title = "test2", content = "content1", timestamp = Date.from(Instant.now()).time, color = Color.RED))
            assertEquals("test2", noteRepository.sortByColorAsc().last().title)
        }
    }

    @After
    fun tearDown() {

    }
}