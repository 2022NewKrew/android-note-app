package com.survivalcoding.noteapp.presentation.addedit

import androidx.lifecycle.*
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.usecase.InsertNoteUseCase
import kotlinx.coroutines.launch

class AddEditViewModel(private val insertNoteUseCase: InsertNoteUseCase) : ViewModel() {
    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note> get() = _note

    fun insertNote(note: Note) = viewModelScope.launch {
        insertNoteUseCase(note)
    }
}

@Suppress("UNCHECKED_CAST")
class AddEditViewModelFactory(
    private val repository: NoteRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEditViewModel::class.java))
            return AddEditViewModel(InsertNoteUseCase(repository)) as T
        else throw IllegalArgumentException()
    }
}