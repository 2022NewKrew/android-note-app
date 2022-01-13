package com.survivalcoding.noteapp.presentation.add_edit_note

import android.text.Editable
import android.view.View
import androidx.lifecycle.*
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.domain.model.Color
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.usecase.InsertNoteUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AddEditNoteViewModel(
    private val insertNoteUseCase: InsertNoteUseCase
) : ViewModel() {

    private var id = -1
    private val mode: Mode
        get() = if (id == -1) Mode.CREATE_MODE else Mode.EDIT_MODE

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _editNoteUiState = MutableLiveData(EditNoteUiState(color = Color.defaultColor))
    val editNoteUiState: LiveData<EditNoteUiState> = _editNoteUiState

    fun setId(id: Int) {
        this.id = id
    }

    fun setTitleText(title: Editable) {
        _editNoteUiState.value = _editNoteUiState.value?.copy(title = title)
    }

    fun setContentText(content: Editable) {
        _editNoteUiState.value = _editNoteUiState.value?.copy(content = content)
    }

    fun setColor(color: Color) {
        _editNoteUiState.value = _editNoteUiState.value?.copy(color = color)
    }

    fun saveNote() {
        if (editNoteUiState.value?.title.isNullOrBlank()) {
            sendEvent(Event.ShowSnackBarEvent(R.string.title_empty, null, null))
            return
        }
        if (editNoteUiState.value?.content.isNullOrBlank()) {
            sendEvent(Event.ShowSnackBarEvent(R.string.content_empty, null, null))
            return
        }

        if (mode == Mode.CREATE_MODE) {
            addNote(editNoteUiState.value ?: return)
        } else {
            modifyNote(editNoteUiState.value ?: return)
        }

        sendEvent(Event.NavigateToNotesEvent)
    }

    private fun addNote(editNoteUiState: EditNoteUiState) {
        viewModelScope.launch {
            insertNoteUseCase(
                Note(
                    title = editNoteUiState.title.toString(),
                    content = editNoteUiState.content.toString(),
                    color = editNoteUiState.color.value,
                )
            )
        }
    }

    private fun modifyNote(editNoteUiState: EditNoteUiState) {
        viewModelScope.launch {
            insertNoteUseCase(
                Note(
                    id = id,
                    title = editNoteUiState.title.toString(),
                    content = editNoteUiState.content.toString(),
                    color = editNoteUiState.color.value,
                )
            )
        }
    }

    private fun sendEvent(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    enum class Mode {
        EDIT_MODE,
        CREATE_MODE
    }

    sealed class Event {
        object NavigateToNotesEvent: Event()
        class ShowSnackBarEvent(
            val messageResourceId: Int,
            val actionTextResourceId: Int?,
            val action: View.OnClickListener?
        ) : Event()
    }
}

@Suppress("UNCHECKED_CAST")
class AddEditNoteViewModelFactory(private val insertNoteUseCase: InsertNoteUseCase) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEditNoteViewModel::class.java)) {
            return AddEditNoteViewModel(insertNoteUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}