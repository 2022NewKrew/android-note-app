package com.survivalcoding.noteapp.presentation.note.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.domain.model.NoteItem
import com.survivalcoding.noteapp.domain.usecase.DeleteNoteUseCase
import com.survivalcoding.noteapp.domain.usecase.GetAllNotesUseCase
import com.survivalcoding.noteapp.domain.usecase.GetNoteByIdUseCase
import com.survivalcoding.noteapp.domain.usecase.UpsertNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
  private val getAllNotesUseCase: GetAllNotesUseCase,
  private val deleteNoteUseCase: DeleteNoteUseCase,
  private val upsertNoteUseCase: UpsertNoteUseCase,
  private val getNoteByIdUseCase: GetNoteByIdUseCase
) : ViewModel() {

  fun getNotes() = getAllNotesUseCase().asLiveData()
  suspend fun getNoteById(id: Int) = viewModelScope.launch { getNoteByIdUseCase(id) }
  suspend fun deleteNote(noteItem: NoteItem) = viewModelScope.launch { deleteNoteUseCase(noteItem) }
  suspend fun upsertNote(noteItem: NoteItem) = viewModelScope.launch { upsertNoteUseCase(noteItem) }
}
