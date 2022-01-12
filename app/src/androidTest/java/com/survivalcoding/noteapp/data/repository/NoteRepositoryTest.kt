package com.survivalcoding.noteapp.data.repository

import android.content.Context
import android.graphics.Color
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.survivalcoding.noteapp.data.datasource.NoteDatabase
import com.survivalcoding.noteapp.data.datasource.NoteRoomDataSource
import com.survivalcoding.noteapp.domain.model.Note
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.Instant
import java.util.*

@RunWith(AndroidJUnit4::class)
class NoteRepositoryTest {

    private lateinit var repository: NoteRepositoryImpl
    private lateinit var db: NoteDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context,
            NoteDatabase::class.java
        ).build()

        repository = NoteRepositoryImpl(NoteRoomDataSource(db.noteDao()))
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertTest() = runBlocking {
        val note = Note(
            id = 1,
            title = "test1",
            content = "content1",
            timestamp = Date.from(Instant.now()).time,
            color = Color.RED
        )
        repository.insertNote(note)
        assertEquals(1, repository.sortByColorAsc().size)

        val newNote = Note(
            id = 2,
            title = "test2",
            content = "content2",
            timestamp = Date.from(Instant.now()).time,
            color = Color.RED
        )
        repository.insertNote(newNote)
        assertEquals(2, repository.sortByColorAsc().size)
    }

    @Test
    fun sortTest() = runBlocking {
        repository.insertNote(
            Note(
                id = 1,
                title = "test1",
                content = "content1",
                timestamp = Date.from(Instant.now()).time,
                color = Color.RED
            )
        )
        repository.insertNote(
            Note(
                id = 2,
                title = "test2",
                content = "content2",
                timestamp = Date.from(Instant.now()).time,
                color = Color.RED
            )
        )

        assertEquals("test2", repository.sortByTimestampDesc().first().title)
    }
}