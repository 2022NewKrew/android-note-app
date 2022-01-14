package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.data.repository.NoteMemoryRepository
import com.survivalcoding.noteapp.domain.NoteOrderBy
import com.survivalcoding.noteapp.domain.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class GetNotesOrderByUseCaseTest {

    private lateinit var getNoteOrderByUseCase: GetNotesOrderByUseCase
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
        getNoteOrderByUseCase = GetNotesOrderByUseCase(noteMemoryRepository)
    }

    @Test
    fun updateData() = runBlocking {
        val noteOrderByFlow = MutableStateFlow(NoteOrderBy.TITLE)
        val isAscFlow = MutableStateFlow(true)

        val notes = getNoteOrderByUseCase(noteOrderByFlow, isAscFlow)

        noteMemoryRepository.upsert(Note(31))

        assertEquals((testNotes + listOf(Note(31))).sortedBy { it.title }, notes.first())
    }

    @Test
    fun updateComparator() = runBlocking {
        val noteOrderByFlow = MutableStateFlow(NoteOrderBy.TITLE)
        val isAscFlow = MutableStateFlow(true)

        val notes = getNoteOrderByUseCase(noteOrderByFlow, isAscFlow)

        noteOrderByFlow.value = NoteOrderBy.COLOR

        assertEquals(testNotes.sortedBy { it.color }, notes.first())
    }

    @Test
    fun updateIsAsc() = runBlocking {
        val noteOrderByFlow = MutableStateFlow(NoteOrderBy.TITLE)
        val isAscFlow = MutableStateFlow(true)

        val notes = getNoteOrderByUseCase(noteOrderByFlow, isAscFlow)

        isAscFlow.value = false

        assertEquals(testNotes.sortedBy { it.title }.reversed(), notes.first())
    }
}