package com.survivalcoding.note_app.presentation.add_edit_note

import android.graphics.Color
import androidx.lifecycle.*
import com.survivalcoding.core.util.SingleLiveEvent
import com.survivalcoding.note_app.domain.model.Note
import com.survivalcoding.note_app.domain.repository.NoteRepository
import kotlinx.coroutines.launch

class AddEditNoteViewModel(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _noteColor = MutableLiveData<Int>(Color.RED)
    val noteColor: LiveData<Int> = _noteColor

    private val _event = SingleLiveEvent<UiEvent>()
    val event: LiveData<UiEvent> = _event

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    repository.getNoteById(noteId)?.also { note ->
                        currentNoteId = note.id
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddEditNoteEvent.SaveNote -> {
                when {
                    event.title.isBlank() -> {
                        _event.value = UiEvent.ShowSnackbar("타이틀을 입력해 주세요")
                    }
                    event.content.isBlank() -> {
                        _event.value = UiEvent.ShowSnackbar("내용을 입력해 주세요")
                    }
                    else -> saveNote(event)
                }
            }
        }
    }

    private fun saveNote(event: AddEditNoteEvent.SaveNote) = viewModelScope.launch {
        repository.insertNote(
            Note(
                title = event.title,
                content = event.content,
                timestamp = System.currentTimeMillis(),
                color = noteColor.value!!,
                id = currentNoteId,
            )
        )
        _event.value = UiEvent.SaveNote
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}