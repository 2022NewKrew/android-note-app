package com.example.data.repository_impl

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.db.note.NoteDatabase
import com.example.domain.entity.Note
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class NoteRepositoryImplTest {

    private lateinit var repositoryImpl: NoteRepositoryImpl

    @Before
    fun setUp() {
        repositoryImpl = NoteRepositoryImpl(
            Room.databaseBuilder(
                ApplicationProvider.getApplicationContext(),
                NoteDatabase::class.java, NoteDatabase.DATABASE_NAME
            ).build()
        )
    }

    @After
    fun tearDown() {
        // dao.close()
    }

    @Test
    fun insertTest() = runBlocking {
        // input sample Note
        repositoryImpl.insertNote(
            Note(
                id = 99L,
                title = "title",
                content = "content",
                timestamp = Date().time,
                color = 0
            )
        )

        repositoryImpl.insertNote( //동일한 값(무시될 값)
            Note(
                id = 99L,
                title = "title",
                content = "content",
                timestamp = Date().time,
                color = 0
            )
        )

        repositoryImpl.insertNote( // 값 추가
            Note(
                id = 999L,
                title = "title",
                content = "content",
                timestamp = Date().time,
                color = 0
            )
        )
        assertEquals(2, repositoryImpl.getNotes().first().size)
    }

    @Test
    fun deleteTest() = runBlocking {
        // 값 추가
        val sample1 = Note(
            id = 99L,
            title = "title",
            content = "content",
            timestamp = Date().time,
            color = 0
        )
        val sample2 = Note(
            id = 999L,
            title = "title",
            content = "content",
            timestamp = Date().time,
            color = 0
        )
        repositoryImpl.insertNote(
            sample1
        )
        repositoryImpl.insertNote(
            sample2
        )
        // 동일 값 삭제 테스트
        repositoryImpl.deleteNote(sample2)
        repositoryImpl.deleteNote(sample2)

        assertEquals(1, repositoryImpl.getNotes().first().size)
    }

    @Test
    fun updateTest() = runBlocking {
        // 값 추가
        val sample1 = Note(
            id = 99L,
            title = "title",
            content = "content",
            timestamp = Date().time,
            color = 0
        )
        // 값 변경
        val sample2 = sample1.copy(title = "update")
        repositoryImpl.updateNote(sample2)

        assertEquals("update", repositoryImpl.getNotes().first()[0].title)
    }
}