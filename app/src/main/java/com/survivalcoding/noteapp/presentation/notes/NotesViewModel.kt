package com.survivalcoding.noteapp.presentation.notes

import android.app.Application
import androidx.lifecycle.*
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NotesRepository
import com.survivalcoding.noteapp.domain.usecase.SortNotesUseCase
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class NotesViewModel(
    application: Application,
    private val notesRepository: NotesRepository,
    private val sortNotesUseCase: SortNotesUseCase,
) : AndroidViewModel(application) {
    private var _notes = MutableLiveData<List<Note>>()
    private var _filter = MutableLiveData<Int>()
    private var _sort = MutableLiveData<Int>()

    init {
        viewModelScope.launch {
            _notes.value = sortNotesUseCase.invoke(
                getValueFromFilterId(R.id.dateButton),
                getValueFromSortId(R.id.descendingButton)
            )
            _filter.value = R.id.dateButton
            _sort.value = R.id.descendingButton
        }
    }

    //UiState를 굳이 LiveData에서 사용하고 싶다면 이게 베스트, 이걸 LiveData 만을 이용해 할 수 있는 방법은??
    val uiState: LiveData<NotesUiState> =
        combine(_notes.asFlow(), _filter.asFlow(), _sort.asFlow()) { notes, filter, sort ->
            NotesUiState(notes, filter, sort)
        }.asLiveData()


    suspend fun getNoteById(id: Int): Note? = notesRepository.getNoteById(id)

    fun insertNote(note: Note) {
        viewModelScope.launch {
            notesRepository.insertNote(note)
            _notes.value = sortNotesUseCase.invoke(
                getValueFromFilterId(_filter.value!!),
                getValueFromSortId(_sort.value!!)
            )
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notesRepository.deleteNote(note)
            _notes.value = sortNotesUseCase.invoke(
                getValueFromFilterId(_filter.value!!),
                getValueFromSortId(_sort.value!!)
            )
        }
    }

    fun updateFilter(filter: Int) {
        _filter.value = filter
    }

    fun updateSort(sort: Int) {
        _sort.value = sort
    }

    fun sortNotes() {
        viewModelScope.launch {
            _notes.value = sortNotesUseCase.invoke(
                getValueFromFilterId(_filter.value!!),
                getValueFromSortId(_sort.value!!)
            )
        }
    }
}

private fun getValueFromSortId(checkedId: Int): Int {
    return when (checkedId) {
        R.id.ascendingButton -> SortNotesUseCase.SORT_ASC
        R.id.descendingButton -> SortNotesUseCase.SORT_DESC
        else -> -1
    }
}

private fun getValueFromFilterId(checkedId: Int): Int {
    return when (checkedId) {
        R.id.titleButton -> SortNotesUseCase.BY_TITLE
        R.id.dateButton -> SortNotesUseCase.BY_DATE
        R.id.colorButton -> SortNotesUseCase.BY_COLOR
        else -> -1
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
                sortNotesUseCase = SortNotesUseCase(notesRepository)
            ) as T
        else throw IllegalArgumentException()
    }
}