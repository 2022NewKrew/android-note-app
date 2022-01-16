package com.survivalcoding.noteapp.presentation.notes

import androidx.lifecycle.*
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.model.SortFactor
import com.survivalcoding.noteapp.domain.model.SortType
import com.survivalcoding.noteapp.domain.usecase.*
import kotlinx.coroutines.launch

class NotesViewModel(
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getNotesUseCase: GetNotesUseCase,
    private val insertNoteUseCase: InsertNoteUseCase
) : ViewModel() {
    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes
    private val _sortFactor = MutableLiveData(SortFactor.TIMESTAMP)
    val sortFactor: LiveData<SortFactor> get() = _sortFactor
    private val _sortType = MutableLiveData(SortType.DESC)
    val sortType: LiveData<SortType> get() = _sortType
    private var recentlyDeletedNote: Note? = null

    fun getNotes() = viewModelScope.launch {
        _notes.value = getNotesUseCase.invoke(
            sortFactor.value ?: SortFactor.TIMESTAMP,
            sortType.value ?: SortType.DESC
        )
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        recentlyDeletedNote = note
        deleteNoteUseCase(note)
        getNotes()
    }

    fun undoDelete() = viewModelScope.launch {
        insertNoteUseCase(recentlyDeletedNote ?: return@launch)
        recentlyDeletedNote = null
        getNotes()
    }

    fun setSortFactor(factor: SortFactor) {
        _sortFactor.value = factor
    }

    fun setSortType(type: SortType) {
        _sortType.value = type
    }
}