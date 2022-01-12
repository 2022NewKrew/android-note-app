package com.example.domain.usecase

import com.example.domain.entity.Note
import com.example.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class DefaultRepositoryTest {
    val repository: NoteRepository = Mockito.mock(NoteRepository::class.java)

    init {
        val notes = notesFlowMaker(SharedContainer.DBTestSize)
        // for getAll
        Mockito.`when`(repository.getNotes()).thenReturn(
            notes
        )
        //for getById
        for (x in 1..100) {
            Mockito.`when`(repository.getNoteById(x.toLong())).thenReturn(
                flow {
                    emit(notes.value.find { it.id == x.toLong() })
                }
            )
        }
    }


    fun notesSet(notesSize: Int) {
        val notes = notesFlowMaker(notesSize)
        Mockito.`when`(repository.getNotes()).thenReturn(
            notes
        )
    }

    private fun notesFlowMaker(size: Int): MutableStateFlow<MutableList<Note>> =
        MutableStateFlow(
            (1L..size.toLong()).map {
                Note(
                    id = it,
                    title = "title $it",
                    content = "content $it",
                    timestamp = Date().time,
                    color = 0xFFFFFF
                )
            }.toMutableList()
        )

}