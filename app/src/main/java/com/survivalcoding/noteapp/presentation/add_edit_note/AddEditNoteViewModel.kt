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
    var id = Date().time
    var title = ""
    var content = ""
    var backgroundColor = AddEditNoteFragment.COLOR_1

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