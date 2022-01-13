package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.data.repository.NoteMemoryRepository
import com.survivalcoding.noteapp.domain.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class GetFlowOrderByUseCaseTest {

    private lateinit var getNoteOrderByUseCase: GetFlowOrderByUseCase<Note>
    private lateinit var noteMemoryRepository: NoteMemoryRepository
    private val testNotes =
        (1..30).map {
            Random.run {
                Note(
                    it,
                    "${nextInt(0, 1000)}",
                    "${nextInt(0, 1000)}"
                )
            }
        }

    @Before
    fun setUp() = runBlocking {
        noteMemoryRepository = NoteMemoryRepository()
        testNotes.forEach { noteMemoryRepository.upsert(it) }
        getNoteOrderByUseCase = GetFlowOrderByUseCase(noteMemoryRepository)
    }

    @Test
    fun updateData() = runBlocking {
        val comparatorFlow = MutableStateFlow(compareBy<Note> { it.title })
        val isAscFlow = MutableStateFlow(true)

        val notes = getNoteOrderByUseCase(comparatorFlow, isAscFlow)

        noteMemoryRepository.upsert(Note(31))

        assertEquals((testNotes + listOf(Note(31))).sortedBy { it.title }, notes.first())
    }

    @Test
    fun updateComparator() = runBlocking {
        val comparatorFlow = MutableStateFlow(compareBy<Note> { it.title })
        val isAscFlow = MutableStateFlow(true)

        val notes = getNoteOrderByUseCase(comparatorFlow, isAscFlow)

        comparatorFlow.value = compareBy { it.color }

        assertEquals(testNotes.sortedBy { it.color }, notes.first())
    }

    @Test
    fun updateIsAsc() = runBlocking {
        val comparatorFlow = MutableStateFlow(compareBy<Note> { it.title })
        val isAscFlow = MutableStateFlow(true)

        val notes = getNoteOrderByUseCase(comparatorFlow, isAscFlow)

        isAscFlow.value = false

        assertEquals(testNotes.sortedBy { it.title }.reversed(), notes.first())
    }
}