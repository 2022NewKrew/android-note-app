package com.survivalcoding.noteapp.presentation.notes

import androidx.lifecycle.*
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.usecase.DeleteNoteUseCase
import com.survivalcoding.noteapp.domain.usecase.GetNotesUseCase
import kotlinx.coroutines.launch

class NotesViewModel(
    getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : ViewModel() {
    private var _uiState = UiState()
    val notes: LiveData<List<Note>> = getNotesUseCase().asLiveData()

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase(note)
        }
    }

    fun setUi(
        key: String? = null,
        mode: Int? = null,
        isVisible: Int? = null,
    ) {
        key?.let { _uiState = _uiState.copy(sortKey = key) }
        mode?.let { _uiState = _uiState.copy(sortMode = mode) }
        isVisible?.let { _uiState = _uiState.copy(sortVisible = isVisible) }
    }

    fun getUiState() = _uiState
}

@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory(
    private val repository: NoteRepository,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java))
            return NotesViewModel(
                getNotesUseCase = GetNotesUseCase(repository),
                deleteNoteUseCase = DeleteNoteUseCase(repository),
            ) as T
        else throw IllegalArgumentException()
    }
}