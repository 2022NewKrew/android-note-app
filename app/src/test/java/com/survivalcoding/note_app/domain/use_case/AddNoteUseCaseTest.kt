package com.survivalcoding.note_app.domain.use_case

import com.survivalcoding.note_app.domain.model.InvalidNoteException
import com.survivalcoding.note_app.domain.model.Note
import com.survivalcoding.note_app.domain.repository.NoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddNoteUseCaseTest {

    @Mock
    private lateinit var mockNoteRepository: NoteRepository

    private lateinit var useCase: AddNoteUseCase

    @Before
    fun setUp() {
        useCase = AddNoteUseCase(mockNoteRepository)
    }

    @Test(expected = InvalidNoteException::class)
    fun addNote() = runBlocking {
        useCase.invoke(Note(title = "", content = "aaa", color = 1))
    }

    @Test(expected = InvalidNoteException::class)
    fun addNote2() = runBlocking {
        useCase.invoke(Note(title = "aaa", content = "", color = 1))
    }

    @Test
    fun addNote3() = runBlocking {
        useCase.invoke(Note(title = "aaa", content = "aaa", color = 1))
    }
}