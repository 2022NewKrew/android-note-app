package com.survivalcoding.noteapp.presentation.notes

import androidx.lifecycle.*
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.usecase.*
import kotlinx.coroutines.launch

class NotesViewModel(
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val sortByColorAscUseCase: SortByColorAscUseCase,
    private val sortByColorDescUseCase: SortByColorDescUseCase,
    private val sortByTimestampAscUseCase: SortByTimestampAscUseCase,
    private val sortByTimestampDescUseCase: SortByTimestampDescUseCase,
    private val sortByTitleAscUseCase: SortByTitleAscUseCase,
    private val sortByTitleDescUseCase: SortByTitleDescUseCase
) : ViewModel() {
    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes
    private val _sortFactor = MutableLiveData(SORT_BY_TIMESTAMP)
    val sortFactor: LiveData<String> get() = _sortFactor
    private val _sortType = MutableLiveData(SORT_DESC)
    val sortType: LiveData<String> get() = _sortType

    fun getNotes() {
        viewModelScope.launch {
            when (sortFactor.value) {
                // color 기준 정렬
                SORT_BY_COLOR -> {
                    _notes.value =
                        if (sortType.value.equals(SORT_ASC)) sortByColorAscUseCase()
                        else sortByColorDescUseCase()
                }
                // title 기준 정렬
                SORT_BY_TITLE -> {
                    _notes.value =
                        if (sortType.value.equals(SORT_ASC)) sortByTitleAscUseCase()
                        else sortByTitleDescUseCase()
                }
                // timestamp 기준 정렬
                SORT_BY_TIMESTAMP -> {
                    _notes.value =
                        if (sortType.value.equals(SORT_ASC)) sortByTimestampAscUseCase()
                        else sortByTimestampDescUseCase()
                }
            }
        }
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        deleteNoteUseCase(note)
        getNotes()
    }

    companion object {
        const val SORT_ASC = "ASC"
        const val SORT_DESC = "DESC"

        const val SORT_BY_COLOR = "color"
        const val SORT_BY_TITLE = "title"
        const val SORT_BY_TIMESTAMP = "timestamp"
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
                SortByColorAscUseCase(repository),
                SortByColorDescUseCase(repository),
                SortByTimestampAscUseCase(repository),
                SortByTimestampDescUseCase(repository),
                SortByTitleAscUseCase(repository),
                SortByTitleDescUseCase(repository)
            ) as T
        else throw IllegalArgumentException()
    }
}