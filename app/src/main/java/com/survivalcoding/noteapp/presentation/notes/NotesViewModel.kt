package com.survivalcoding.noteapp.presentation.notes

import android.app.Application
import androidx.lifecycle.*
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NotesRepository
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

class NotesViewModel(
    application: Application,
    private val notesRepository: NotesRepository
) : AndroidViewModel(application) {
    private var notes: LiveData<List<Note>> = notesRepository.getNotes().asLiveData()
    private var deletedNote : Note? = null

    suspend fun getNoteById(id: Int): Note? = notesRepository.getNoteById(id)

    fun insertNote(note: Note) {
        viewModelScope.launch {
            notesRepository.insertNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notesRepository.deleteNote(note)
            deletedNote = note
        }
    }

    fun restoreNote() {
        viewModelScope.launch {
            deletedNote?.let {
                notesRepository.insertNote(it)
            }
            deletedNote = null
        }
    }

    fun filterNotes(filter: Int, sort: Int){
        viewModelScope.launch{
            //LiveData는 _notes.value = filtering() 이런 식으로 알려줄 수 있는데
            //Flow는 어떻게??
            //생각나는건 NoteDao에 filtering 관련으로 정렬된 채 오는 쿼리문을 작성해서 이를 Flow로 받게 하는 것
            //근데 이게 과연 효율적인가??
        }
    }
}

class NotesViewModelFactory(
    private val application: Application,
    private val notesRepository: NotesRepository
) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java))
            return NotesViewModel(
                application = application,
                notesRepository = notesRepository
            ) as T
        else throw IllegalArgumentException()
    }
}