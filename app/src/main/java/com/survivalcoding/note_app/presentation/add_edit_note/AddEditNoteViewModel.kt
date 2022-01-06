package com.survivalcoding.note_app.presentation.add_edit_note

import android.graphics.Color
import androidx.lifecycle.*
import com.survivalcoding.note_app.domain.model.Note
import com.survivalcoding.note_app.domain.repository.NoteRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class AddEditNoteViewModel(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _noteColor = MutableLiveData<Int>()
    val noteColor: LiveData<Int> = _noteColor

    private val _noteTitle = MutableLiveData<String>()
    val noteTitle: LiveData<String> = _noteTitle

    private val _noteContent = MutableLiveData<String>()
    val noteContent: LiveData<String> = _noteContent

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            viewModelScope.launch {
                repository.getNoteById(noteId)?.also { note ->
                    currentNoteId = note.id
                    _noteTitle.value = note.title
                    _noteContent.value = note.content
                    _noteColor.value = note.color
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
                        viewModelScope.launch {
                            _event.emit(UiEvent.ShowSnackBar("타이틀을 입력해 주세요"))
                        }
                    }
                    event.content.isBlank() -> {
                        viewModelScope.launch {
                            _event.emit(UiEvent.ShowSnackBar("내용을 입력해 주세요"))
                        }
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
                color = noteColor.value ?: Color.RED,
                id = currentNoteId,
            )
        )

        _event.emit(UiEvent.SaveNote)
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}