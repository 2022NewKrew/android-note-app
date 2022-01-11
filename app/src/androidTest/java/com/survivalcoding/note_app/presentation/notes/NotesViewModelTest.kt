package com.survivalcoding.note_app.presentation.notes

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.survivalcoding.note_app.data.data_source.NoteDao
import com.survivalcoding.note_app.data.data_source.NoteDatabase
import com.survivalcoding.note_app.data.repository.NoteRepositoryImpl
import com.survivalcoding.note_app.domain.model.Note
import com.survivalcoding.note_app.domain.use_case.AddNoteUseCase
import com.survivalcoding.note_app.domain.use_case.DeleteNoteUseCase
import com.survivalcoding.note_app.domain.use_case.GetNotesUseCase
import com.survivalcoding.note_app.domain.use_case.NoteUseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual


@RunWith(AndroidJUnit4::class)
class NotesViewModelTest {
    private lateinit var noteDao: NoteDao
    private lateinit var db: NoteDatabase
    private lateinit var repository: NoteRepositoryImpl
    private lateinit var viewModel: NotesViewModel
    private lateinit var useCases: NoteUseCases

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java).build()
        noteDao = db.noteDao

        repository = NoteRepositoryImpl(noteDao)

        useCases = NoteUseCases(
            getNotes = GetNotesUseCase(repository),
            deleteNote = DeleteNoteUseCase(repository),
            addNote = AddNoteUseCase(repository),
        )

        viewModel = NotesViewModel(useCases)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun viewModelTest() = runBlocking {
        val note = Note(id = 1, title = "test", content = "test", color = 1)
        useCases.addNote(note)

        viewModel.getNotes()
        delay(1000)

        assertThat(viewModel.state.value.notes.size, IsEqual(1))

        viewModel.onEvent(NotesEvent.DeleteNote(note))
        delay(1000)

        assertThat(viewModel.state.value.notes.size, IsEqual(0))
    }
}