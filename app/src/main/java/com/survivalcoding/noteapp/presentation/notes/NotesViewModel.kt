package com.survivalcoding.noteapp.presentation.notes

import android.app.Application
import androidx.lifecycle.*
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.Order
import com.survivalcoding.noteapp.domain.model.SortBy
import com.survivalcoding.noteapp.domain.repository.NotesRepository
import com.survivalcoding.noteapp.domain.usecase.GetSortedNotesUseCase
import com.survivalcoding.noteapp.presentation.id2Order
import com.survivalcoding.noteapp.presentation.id2SortBy
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class NotesViewModel(
    application: Application,
    private val notesRepository: NotesRepository,
    private val getSortedNotesUseCase: GetSortedNotesUseCase,
) : AndroidViewModel(application) {
    private var _notes = MutableLiveData<List<Note>>()
    private var _sortBy = MutableLiveData<SortBy>()
    private var _order = MutableLiveData<Order>()

    init {
        viewModelScope.launch {
            _notes.value = getSortedNotesUseCase.invoke(
                id2SortBy(R.id.dateButton),
                id2Order(R.id.descendingButton)
            )
            _sortBy.value = id2SortBy(R.id.dateButton)
            _order.value = id2Order(R.id.descendingButton)
        }
    }

    val uiState: LiveData<NotesUiState> =
        combine(_notes.asFlow(), _sortBy.asFlow(), _order.asFlow()) { notes, sortBy, order ->
            NotesUiState(notes, sortBy, order)
        }.asLiveData()

    fun insertNote(note: Note) {
        viewModelScope.launch {
            notesRepository.insertNote(note)
            _notes.value = getSortedNotesUseCase.invoke(
                _sortBy.value!!, _order.value!!
            )
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notesRepository.deleteNote(note)
            _notes.value = getSortedNotesUseCase.invoke(
                _sortBy.value!!, _order.value!!
            )
        }
    }

    fun updateFilter(id: Int) {
        _sortBy.value = id2SortBy(id)
    }

    fun updateSort(id: Int) {
        _order.value = id2Order(id)
    }

    fun sortNotes() {
        viewModelScope.launch {
            _notes.value = getSortedNotesUseCase.invoke(
                _sortBy.value!!, _order.value!!
            )
        }
    }
}

class NotesViewModelFactory(
    private val application: Application,
    private val notesRepository: NotesRepository,
) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java))
            return NotesViewModel(
                application = application,
                notesRepository = notesRepository,
                getSortedNotesUseCase = GetSortedNotesUseCase(notesRepository)
            ) as T
        else throw IllegalArgumentException()
    }
}