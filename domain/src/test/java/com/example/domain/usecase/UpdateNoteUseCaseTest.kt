/*
package com.example.domain.usecase

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import java.util.*

class UpdateNoteUseCaseTest {

    private val repositoryTest = DefaultRepositoryTest()
    private val repository = repositoryTest.repository
    private val updateNoteAllUseCase = UpdateNoteUseCase(repository)

    @Test
    fun updateTest() = runBlocking {
        var randomId = Random().nextInt(SharedContainer.DBTestSize)
        val note = repository.getNotes().first()[randomId]
        // for update
        runBlocking {
            Mockito.`when`(repository.updateNote(note)).thenReturn(Unit)
        }
        updateNoteAllUseCase(note)
    }
}*/
