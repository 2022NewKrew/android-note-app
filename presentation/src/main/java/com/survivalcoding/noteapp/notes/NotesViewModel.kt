package com.survivalcoding.noteapp.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Note
import com.example.domain.repository.NoteRepository
import com.example.domain.usecase.DeleteNodeUseCase
import com.example.domain.usecase.GetNoteAllUseCase
import com.example.domain.usecase.GetNoteByIdUseCase
import com.survivalcoding.noteapp.notes.MainEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotesViewModel(
    private val getNoteAllUseCase: GetNoteAllUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val deleteNodeUseCase: DeleteNodeUseCase,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    // 우아하지 않아 보인다.
    private val __notes: Flow<List<Note>> = getNoteAllUseCase()
    private var _notes: MutableStateFlow<List<Note>> = MutableStateFlow(listOf())
    val notes: StateFlow<List<Note>> get() = _notes
    private var tmpNote: Note? = null
    private var tmpPosition: Int? = null

    init {
        viewModelScope.launch {
            __notes.collect {
                _notes.value = it
            }
        }
    }

    fun deleteNote() {
        viewModelScope.launch(coroutineDispatcher) {
            tmpNote?.let {
                deleteNodeUseCase(it)
            }
            tmpNote = null
        }
    }

    fun eventListener(event: MainEvent) {
        when (event) {
            is MainEvent.SwipeDeleteEvent -> {
                tempDeleteNote(event.note)
                tmpNote = event.note
                tmpPosition = event.position
            }
            is MainEvent.SortingEvent -> {
                sorting(event.sorted_with, event.ascOrDesc)
            }
        }
    }


    private fun tempDeleteNote(note: Note) {
        tmpNote = note
        _notes.value = notes.value.filter {
            it.id != note.id
        }
    }

    fun unDoDelete() {
        if (tmpNote != null && tmpPosition != null) {
            _notes.value = notes.value.toMutableList().apply {
                add(tmpPosition!!, tmpNote!!)
            }
        }
        tmpNote = null
        tmpPosition = null
    }

    fun sorting(sorted_with: String, ascOrDesc: String) {
        when (ascOrDesc) {
            "ASC" -> {
                if (sorted_with == "TIME") _notes.value = _notes.value.sortedBy { it.title }
                else if (sorted_with == "TITLE") _notes.value = _notes.value.sortedBy { it.title }
                else if (sorted_with == "COLOR") _notes.value = _notes.value.sortedBy { it.color }
            }
            "DESC" -> {
                if (sorted_with == "TIME") _notes.value =
                    _notes.value.sortedByDescending { it.timestamp }
                else if (sorted_with == "TITLE") _notes.value =
                    _notes.value.sortedByDescending { it.title }
                else if (sorted_with == "COLOR") _notes.value =
                    _notes.value.sortedByDescending { it.color }
            }
        }
    }

}

class NotesViewModelFactory(
    private val repository: NoteRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            return NotesViewModel(
                GetNoteAllUseCase(repository),
                GetNoteByIdUseCase(repository),
                DeleteNodeUseCase(repository),
                Dispatchers.IO
            ) as T
        } else throw IllegalArgumentException()
    }
}