package com.example.domain.usecase

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class GetNoteAllUseCaseTest {
    private val repositoryTest = DefaultRepositoryTest()
    private val repository = repositoryTest.repository
    private val getNoteAllUseCase = GetNoteAllUseCase(repository)


    @Test
    fun getAllTest() = runBlocking {
        // check default set size 100
        var notes = getNoteAllUseCase().first()
        assertEquals(SharedContainer.DBTestSize, notes.size)

        //random size testing
        for (x in 1..10) {
            val notesSize = Random().nextInt(1000)
            repositoryTest.notesSet(notesSize)
            notes = getNoteAllUseCase().first()
            assertEquals(notesSize, notes.size)
        }
    }

}