package com.survivalcoding.noteapp.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.survivalcoding.noteapp.data.datasource.database.NoteDatabase
import com.survivalcoding.noteapp.data.datasource.NoteRoomDataSource
import com.survivalcoding.noteapp.domain.model.NoteItem
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteRepositoryImplTest {
  private lateinit var repository: NoteRepository
  private lateinit var db: NoteDatabase

  @Before
  fun setUp() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    db = Room.inMemoryDatabaseBuilder(
      context, NoteDatabase::class.java
    ).build()

    val dao = db.noteDao()

    // val dataSource = NoteMockDataSource()
    val dataSource = NoteRoomDataSource(dao)

    repository = NoteRepositoryImpl(dataSource)
  }

  @After
  fun tearDown() {
    db.close()
  }

  @Test
  fun upsertNote() = runBlocking {
    val note = NoteItem(id = 1, title = "testTitle", content = "testContent", color = 0)
    repository.upsertNote(note)

    Assert.assertEquals(1, repository.getNotes().first().size)

    Assert.assertEquals(note, repository.getNotes().first().first())

    repository.upsertNote(note)
    Assert.assertEquals(1, repository.getNotes().first().size)

    val note2 = NoteItem(id = 2, title = "testTitle2", content = "testContent2", color = 0)

    repository.upsertNote(note2)
    Assert.assertEquals(2, repository.getNotes().first().size)
  }

  @Test
  fun getNotes() = runBlocking {
  }
}
