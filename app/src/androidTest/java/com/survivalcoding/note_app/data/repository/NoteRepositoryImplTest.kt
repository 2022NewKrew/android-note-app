package com.survivalcoding.note_app.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.survivalcoding.note_app.data.data_source.NoteDao
import com.survivalcoding.note_app.data.data_source.NoteDatabase
import com.survivalcoding.note_app.domain.model.Note
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsInstanceOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class NoteRepositoryImplTest {
    private lateinit var noteDao: NoteDao
    private lateinit var db: NoteDatabase
    private lateinit var repository: NoteRepositoryImpl

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java).build()
        noteDao = db.noteDao

        repository = NoteRepositoryImpl(noteDao)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun getNotes() = runBlocking {
        noteDao.insertNote(Note("test", "test", 0, 0))

        val notes = noteDao.getNotes().first()
        assertThat(notes[0].title, IsEqual("test"))
        assertThat(notes[0].content, IsEqual("test"))
        assertThat(notes[0].timestamp, IsEqual(0))
        assertThat(notes[0].color, IsEqual(0))
    }

    @Test
    fun getNoteById() = runBlocking {
        noteDao.insertNote(Note("test", "test", 0, 0, id = 1))

        val note = noteDao.getNoteById(1)

        assertThat(note, IsInstanceOf(Note::class.java))
    }

    @Test
    fun insertNote() = runBlocking {
        val note = Note(
            id = 1,
            title = "test",
            content = "test",
            timestamp = Date().time,
            color = 1,
        )
        repository.insertNote(note)

        assertThat(repository.getNotes().first().first(), IsEqual(note))
    }

    @Test
    fun deleteNote() {
    }
}