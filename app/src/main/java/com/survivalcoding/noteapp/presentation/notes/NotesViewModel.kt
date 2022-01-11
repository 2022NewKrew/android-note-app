package com.survivalcoding.noteapp.presentation.notes

import androidx.lifecycle.*
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.usecase.*
import kotlinx.coroutines.launch

class NotesViewModel(
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val sortByColorAscUseCase: SortByColorAcsUseCase,
    private val sortByColorDecsUseCase: SortByColorDecsUseCase,
    private val sortByTimestampAscUseCase: SortByTimestampAcsUseCase,
    private val sortByTimestampDecsUseCase: SortByTimestampDecsUseCase,
    private val sortByTitleAscUseCase: SortByTitleAcsUseCase,
    private val sortByTitleDecsUseCase: SortByTitleDecsUseCase
) : ViewModel() {
    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes
    private val _sortFactor = MutableLiveData(SORT_DESC)
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
                        else sortByColorDecsUseCase()
                }
                // title 기준 정렬
                SORT_BY_TITLE -> {
                    _notes.value =
                        if (sortType.value.equals(SORT_ASC)) sortByTitleAscUseCase()
                        else sortByTitleDecsUseCase()
                }
                // timestamp 기준 정렬
                SORT_BY_TIMESTAMP -> {
                    _notes.value =
                        if (sortType.value.equals(SORT_ASC)) sortByTimestampAscUseCase()
                        else sortByTimestampDecsUseCase()
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
                SortByColorAcsUseCase(repository),
                SortByColorDecsUseCase(repository),
                SortByTimestampAcsUseCase(repository),
                SortByTimestampDecsUseCase(repository),
                SortByTitleAcsUseCase(repository),
                SortByTitleDecsUseCase(repository)
            ) as T
        else throw IllegalArgumentException()
    }
}