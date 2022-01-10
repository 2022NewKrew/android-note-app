package com.survivalcoding.noteapp.presentation.add_edit_note

import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.launch
import java.util.*

class AddEditNoteViewModel(
    private val notesRepository: NoteRepository
) : ViewModel() {
    var id = -1L
    var title = ""
    var content = ""
    var backgroundColor = Color.CYAN

    fun setColor(selectedColor: Int) {
        backgroundColor = selectedColor
    }

    fun insert() {
        viewModelScope.launch {
            notesRepository.insertNote(
                Note(
                    id = id,
                    title = title,
                    content = content,
                    timestamp = Date().time,
                    color = backgroundColor,
                )
            )
        }
    }

    fun setInfo(mode: Int, note: Note = Note()) {
        if (mode == MODE_ADD) {
            id = Date().time
            title = ""
            content = ""
            backgroundColor = Color.CYAN
        } else {
            id = note.id ?: Date().time
            title = note.title
            content = note.content
            backgroundColor = Color.CYAN
        }
    }

    companion object {
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }
}

@Suppress("UNCHECKED_CAST")
class AddEditNoteViewModelFactory(
    private val repository: NoteRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEditNoteViewModel::class.java))
            return AddEditNoteViewModel(repository) as T
        else throw IllegalArgumentException()
    }
}