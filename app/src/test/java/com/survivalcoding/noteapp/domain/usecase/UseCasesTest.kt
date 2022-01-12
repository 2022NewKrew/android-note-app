package com.survivalcoding.noteapp.domain.usecase

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

class UseCasesTest {
    private lateinit var repository: NoteRepository
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase
    private lateinit var getNotesUseCase: GetNotesUseCase
    private lateinit var insertNoteUseCase: InsertNoteUseCase

    @Before
    fun setUp() {
        repository = object : NoteRepository {
            private var list = mutableListOf<Note>(
                Note(1, "note1", "note content1", Date().time, color = "#1d1d1d"),
                Note(2, "note2", "note content2", Date().time, color = "#1d1d1d"),
            )

            override fun getNotes(): Flow<List<Note>> = flow { emit(list) }
            override suspend fun getNoteById(id: Long): Note = list.first { it.id == id }
            override suspend fun insertNote(vararg notes: Note) {
                notes.forEach { list = list.plus(it).toMutableList() }
            }

            override suspend fun deleteNote(note: Note) {
                list = list.filter { it.id != note.id }.toMutableList()
            }
        }
        deleteNoteUseCase = DeleteNoteUseCase(repository)
        getNotesUseCase = GetNotesUseCase(repository)
        insertNoteUseCase = InsertNoteUseCase(repository)
    }

    @After
    fun tearDown() {
    }

    @Test
    operator fun invoke() = runBlocking {
        val list = getNotesUseCase()
        assertEquals(2, list.first().size)
        deleteNoteUseCase(Note(id = 1))
        assertEquals(1, list.first().size)
        deleteNoteUseCase(Note(id = 1))
        assertEquals(1, list.first().size)
        insertNoteUseCase(Note(id = 3))
        assertEquals(2, list.first().size)
    }
}