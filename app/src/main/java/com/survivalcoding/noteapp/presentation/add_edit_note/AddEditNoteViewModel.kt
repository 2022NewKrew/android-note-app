package com.survivalcoding.noteapp.presentation.add_edit_note

import android.text.Editable
import androidx.lifecycle.*
import com.survivalcoding.noteapp.domain.model.Color
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.usecase.DeleteNoteUseCase
import com.survivalcoding.noteapp.domain.usecase.InsertNoteUseCase
import kotlinx.coroutines.launch

class AddEditNoteViewModel(
    private val insertNoteUseCase: InsertNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private var id = -1
    private val mode: Mode
        get() = if (id == -1) Mode.CREATE_MODE else Mode.EDIT_MODE

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
        if (mode == Mode.CREATE_MODE) {
            addNote(editNoteUiState.value ?: return)
        } else {
            modifyNote(editNoteUiState.value ?: return)
        }
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
            deleteNoteUseCase(
                Note(
                    id = id,
                    title = editNoteUiState.title.toString(),
                    content = editNoteUiState.content.toString(),
                    color = editNoteUiState.color.value,
                )
            )
        }
    }

    enum class Mode {
        EDIT_MODE,
        CREATE_MODE
    }
}

@Suppress("UNCHECKED_CAST")
class AddEditNoteViewModelFactory(
    private val insertNoteUseCase: InsertNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEditNoteViewModel::class.java)) {
            return AddEditNoteViewModel(insertNoteUseCase, deleteNoteUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}