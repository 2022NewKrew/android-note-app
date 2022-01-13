package com.survivalcoding.noteapp.data.repository

import com.survivalcoding.noteapp.domain.model.Note
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NoteMemoryRepositoryTest {

    private lateinit var noteMemoryRepository: NoteMemoryRepository

    @Before
    fun setUp() {
        noteMemoryRepository = NoteMemoryRepository()
    }

    @Test
    fun insert() = runBlocking {
        val note = Note()
        noteMemoryRepository.upsert(note)

        val notes = noteMemoryRepository.selectAll().first()

        assertEquals(1, notes.size)
        assertEquals(note.copy(1), notes.first())
    }

    @Test
    fun update() = runBlocking {
        val note = Note()
        noteMemoryRepository.upsert(note)

        val updateNote = note.copy(id = 1, title = "updated")
        noteMemoryRepository.upsert(updateNote)

        val notes = noteMemoryRepository.selectAll().first()

        assertEquals(1, notes.size)
        assertEquals(updateNote, notes.first())
    }

    @Test
    fun selectAll() = runBlocking {
        val testNotes = listOf(Note(id = 1), Note(id = 2))
        testNotes.forEach { noteMemoryRepository.upsert(it) }

        val notes = noteMemoryRepository.selectAll().first()

        assertEquals(testNotes, notes)
    }

    @Test
    fun selectByIdWhereExist() = runBlocking {
        val testNotes = listOf(Note(id = 1), Note(id = 2))
        testNotes.forEach { noteMemoryRepository.upsert(it) }

        val note = noteMemoryRepository.selectById(1)

        assertEquals(testNotes.first(), note)
    }

    @Test
    fun selectByIdWhereNotExist() = runBlocking {
        val testNotes = listOf(Note(id = 1), Note(id = 2))
        testNotes.forEach { noteMemoryRepository.upsert(it) }
        noteMemoryRepository.delete(testNotes.first())

        val note = noteMemoryRepository.selectById(1)

        assertEquals(null, note)
    }

    @Test
    fun delete() = runBlocking {
        val testNotes = listOf(Note(id = 1), Note(id = 2))
        testNotes.forEach { noteMemoryRepository.upsert(it) }
        noteMemoryRepository.delete(testNotes.first())

        val notes = noteMemoryRepository.selectAll().first()

        assertEquals(1, notes.size)
        assertEquals(notes.filterNot { it.id == 1 }, notes)
    }
}