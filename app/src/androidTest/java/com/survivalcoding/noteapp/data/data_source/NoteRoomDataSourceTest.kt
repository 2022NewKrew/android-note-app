package com.survivalcoding.noteapp.data.data_source

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.survivalcoding.noteapp.domain.model.Color
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.Order
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteRoomDataSourceTest {

    private lateinit var noteRoomInMemoryDatabase: NoteDatabase
    private lateinit var noteRoomInMemoryDao: NoteDao
    private lateinit var noteRoomDataSource: NoteRoomDataSource

    private var notes = mutableListOf<Note>()

    @Before
    fun setUp() {
        runBlocking {
            notes = mutableListOf()
            (1..30).toList().forEach {
                delay(3)
                notes.add(
                    Note(
                        id = it,
                        title = "title $it",
                        content = "content $it",
                        color = Color.defaultColor.value
                    )
                )
            }
        }

        createDb()
        noteRoomDataSource = NoteRoomDataSource(noteRoomInMemoryDao)
    }

    private fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        noteRoomInMemoryDatabase = Room.inMemoryDatabaseBuilder(
            context,
            NoteDatabase::class.java
        ).build()
        noteRoomInMemoryDao = noteRoomInMemoryDatabase.noteDao()
    }

    @After
    fun tearDown() {
        noteRoomInMemoryDatabase.close()
    }

    @Test
    fun 노트_추가하기() {
        runBlocking {
            notes.forEach {
                noteRoomDataSource.insertNote(it)
            }

            val sortedList = noteRoomDataSource.getSortedNotes(Order.TITLE_ASC)
            val sortedWithTitleOrderByAsc = notes.sortedBy { it.title }
            assertEquals(true, sortedWithTitleOrderByAsc == sortedList)
        }
    }

    @Test
    fun 중복_노트_추가하기() {
        runBlocking {
            notes.forEach {
                noteRoomDataSource.insertNote(it)
            }

            // 이미 존재하는 노트 추가
            val insertTargetNote = notes.first()
            noteRoomDataSource.insertNote(insertTargetNote)
            noteRoomDataSource.insertNote(insertTargetNote)
            noteRoomDataSource.insertNote(insertTargetNote)
            noteRoomDataSource.insertNote(insertTargetNote)
            noteRoomDataSource.insertNote(insertTargetNote)
            noteRoomDataSource.insertNote(insertTargetNote)

            // id 동일, 제목 다른 노트 추가
            val insertTargetWithDifferentTitle = notes.first().copy(title = "중복 노트")
            noteRoomDataSource.insertNote(insertTargetWithDifferentTitle)

            var noteList = noteRoomDataSource.getSortedNotes(Order.TITLE_ASC).filter { it.id == insertTargetWithDifferentTitle.id }
            assertEquals(1, noteList.size)
            assertEquals(insertTargetWithDifferentTitle, noteList.first())

            // 새로운 노트 추가
            val newNote = Note(title = "새로운 노트", content = "", color = Color.defaultColor.value)
            noteRoomDataSource.insertNote(newNote)
            noteList = noteRoomDataSource.getSortedNotes(Order.TITLE_ASC).filter { it.title == newNote.title }
            assertEquals(1, noteList.size)
        }
    }

    @Test
    fun 노트_삭제하기() {
        runBlocking {
            notes.forEach {
                noteRoomDataSource.insertNote(it)
            }

            val deleteTargetNote = notes.first()

            // id 동일하고 내용 다른 노트 삭제 테스트
            val noteWithDifferentTitle = deleteTargetNote.copy(title = "중복 노트")
            noteRoomDataSource.deleteNote(noteWithDifferentTitle)

            var noteList = noteRoomDataSource.getSortedNotes(Order.TITLE_ASC)
            var targetNoteList = noteList.filter { it.id == noteWithDifferentTitle.id }
            assertEquals(notes.size - 1, noteList.size)
            assertEquals(0, targetNoteList.size)

            // 완전히 동일한 노트 삭제 테스트
            noteRoomDataSource.insertNote(deleteTargetNote)

            noteRoomDataSource.deleteNote(deleteTargetNote)
            noteRoomDataSource.deleteNote(deleteTargetNote)
            noteRoomDataSource.deleteNote(deleteTargetNote)

            noteList = noteRoomDataSource.getSortedNotes(Order.TITLE_ASC)
            targetNoteList = noteList.filter { it.id == noteWithDifferentTitle.id }
            assertEquals(notes.size - 1, noteList.size)
            assertEquals(0, targetNoteList.size)
        }
    }

    @Test
    fun 정렬된_노트_리스트_가져오기() {
        runBlocking {
            notes.forEach {
                noteRoomDataSource.insertNote(it)
            }

            var resultList = noteRoomDataSource.getSortedNotes(Order.TITLE_ASC)
            val sortedWithTitleOrderByAsc = notes.sortedBy { it.title }
            assertEquals(true, sortedWithTitleOrderByAsc == resultList)

            resultList = noteRoomDataSource.getSortedNotes(Order.TITLE_DESC)
            val sortedWithTitleOrderByDesc = notes.sortedByDescending { it.title }
            assertEquals(true, sortedWithTitleOrderByDesc == resultList)

            resultList = noteRoomDataSource.getSortedNotes(Order.DATE_ASC)
            val sortedWithDateOrderByAsc = notes.sortedBy { it.timestamp }
            assertEquals(true, sortedWithDateOrderByAsc == resultList)

            resultList = noteRoomDataSource.getSortedNotes(Order.DATE_DESC)
            val sortedWithDateOrderByDesc = notes.sortedByDescending { it.timestamp }
            assertEquals(true, sortedWithDateOrderByDesc == resultList)

            resultList = noteRoomDataSource.getSortedNotes(Order.COLOR_ASC)
            val sortedWithColorOrderByAsc = notes.sortedBy { it.color }
            assertEquals(true, sortedWithColorOrderByAsc == resultList)

            resultList = noteRoomDataSource.getSortedNotes(Order.COLOR_DESC)
            val sortedWithColorOrderByDesc = notes.sortedByDescending { it.color }
            assertEquals(true, sortedWithColorOrderByDesc == resultList)
        }
    }
}