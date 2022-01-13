package com.example.domain.usecase

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import java.util.*

class GetNoteByIdUseCaseTest {

    private val repositoryTest = DefaultRepositoryTest()
    private val repository = repositoryTest.repository
    private val getNoteByIdUseCase = GetNoteByIdUseCase(repository)

    @Test
    fun getByIdTest() = runBlocking{
        // check default set
        var randomId = Random().nextInt(SharedContainer.DBTestSize).toLong()
        var note = getNoteByIdUseCase(randomId)
        assertEquals(randomId, note.first()?.id)

        //random index testing
        for (x in 1..10) {
            randomId = Random().nextInt(SharedContainer.DBTestSize).toLong()
            note = getNoteByIdUseCase(randomId)
            assertEquals(randomId, note.first()?.id)
        }
    }

}