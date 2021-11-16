package com.survivalcoding.note_app.domain.use_case

import com.survivalcoding.note_app.domain.model.Note
import com.survivalcoding.note_app.domain.repository.NoteRepository
import com.survivalcoding.note_app.domain.util.NoteOrder
import com.survivalcoding.note_app.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetNotesUseCaseTest {

    @Mock
    private lateinit var mockNoteRepository: NoteRepository

    private val noteList = listOf(
        Note(
            title = "title2",
            content = "content2",
            timestamp = 2,
            color = 2,
        ),
        Note(
            title = "title",
            content = "content",
            timestamp = 1,
            color = 1,
        ),
    )

    @Test
    fun `모든 노트 목록을 가져와야 한다`() {
        val useCase = GetNotesUseCase(mockNoteRepository)

        // Mock을 이용하여 getNotes()를 호출시 항상 noteList를 반환하도록 지정
        `when`(mockNoteRepository.getNotes()).thenAnswer { flow { emit(noteList) } }

        var result = runBlocking {
            useCase().first()
        }
        assertThat(result, IsEqual(noteList))
        // Repository에서 메서드가 호출되었는지 검증
        verify(mockNoteRepository, atLeastOnce()).getNotes()

        result = runBlocking {
            useCase(NoteOrder.Color(OrderType.Ascending)).first()
        }
        assertThat(result.first().color, IsEqual(1))
        verify(mockNoteRepository, atLeastOnce()).getNotes()

        result = runBlocking {
            useCase(NoteOrder.Color(OrderType.Descending)).first()
        }
        assertThat(result.first().color, IsEqual(2))
        verify(mockNoteRepository, atLeastOnce()).getNotes()

        result = runBlocking {
            useCase(NoteOrder.Title(OrderType.Ascending)).first()
        }
        assertThat(result.first().title, IsEqual("title"))
        verify(mockNoteRepository, atLeastOnce()).getNotes()

        result = runBlocking {
            useCase(NoteOrder.Title(OrderType.Descending)).first()
        }
        assertThat(result.first().title, IsEqual("title2"))
        verify(mockNoteRepository, atLeastOnce()).getNotes()

        result = runBlocking {
            useCase(NoteOrder.Date(OrderType.Ascending)).first()
        }
        assertThat(result.first().timestamp, IsEqual(1))
        verify(mockNoteRepository, atLeastOnce()).getNotes()

        result = runBlocking {
            useCase(NoteOrder.Date(OrderType.Descending)).first()
        }
        assertThat(result.first().timestamp, IsEqual(2))
        verify(mockNoteRepository, atLeastOnce()).getNotes()

        // Repository에서 메서드가 더 이상 호출되지 않았음을 검증
        verifyNoMoreInteractions(mockNoteRepository)
    }
}