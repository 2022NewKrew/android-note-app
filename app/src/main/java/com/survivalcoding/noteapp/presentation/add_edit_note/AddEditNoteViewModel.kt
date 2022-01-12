package com.survivalcoding.noteapp.presentation.add_edit_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.usecase.InsertNoteUseCase
import kotlinx.coroutines.launch

class AddEditNoteViewModel(
    private val insertNoteUseCase: InsertNoteUseCase
) : ViewModel() {
    private var currentNote = Note()

    fun setNote(
        title: String? = null,
        content: String? = null,
        color: String? = null,
        timeStamp: Long? = null,
    ) {
        title?.let { currentNote = currentNote.copy(title = title) }
        content?.let { currentNote = currentNote.copy(content = content) }
        color?.let { currentNote = currentNote.copy(color = color) }
        timeStamp?.let { currentNote = currentNote.copy(timestamp = timeStamp) }
    }

    fun setNote(note: Note) {
        currentNote = note
    }

    fun getNote() = currentNote

    fun insert() {
        viewModelScope.launch {
            insertNoteUseCase(currentNote)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class AddEditNoteViewModelFactory(
    private val repository: NoteRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEditNoteViewModel::class.java))
            return AddEditNoteViewModel(
                InsertNoteUseCase(repository)
            ) as T
        else throw IllegalArgumentException()
    }
}