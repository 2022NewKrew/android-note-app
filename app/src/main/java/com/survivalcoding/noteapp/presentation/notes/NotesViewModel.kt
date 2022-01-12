package com.survivalcoding.noteapp.presentation.notes

import androidx.lifecycle.*
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.SortFactor
import com.survivalcoding.noteapp.domain.model.SortType
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.usecase.*
import kotlinx.coroutines.launch

class NotesViewModel(
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getNotesUseCase: GetNotesUseCase
) : ViewModel() {
    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes
    private val _sortFactor = MutableLiveData(SortFactor.TIMESTAMP)
    val sortFactor: LiveData<SortFactor> get() = _sortFactor
    private val _sortType = MutableLiveData(SortType.DESC)
    val sortType: LiveData<SortType> get() = _sortType

    fun getNotes() = viewModelScope.launch {
        _notes.value = getNotesUseCase.invoke(
            sortFactor.value ?: SortFactor.TIMESTAMP,
            sortType.value ?: SortType.DESC
        )
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        deleteNoteUseCase(note)
        getNotes()
    }
}

@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory(
    private val repository: NoteRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java))
            return NotesViewModel(
                DeleteNoteUseCase(repository),
                GetNotesUseCase(
                    SortByColorAscUseCase(repository),
                    SortByColorDescUseCase(repository),
                    SortByTimestampAscUseCase(repository),
                    SortByTimestampDescUseCase(repository),
                    SortByTitleAscUseCase(repository),
                    SortByTitleDescUseCase(repository),
                )
            ) as T
        else throw IllegalArgumentException()
    }
}