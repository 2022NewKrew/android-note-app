package com.survivalcoding.noteapp.presentation.note.list

import androidx.lifecycle.ViewModel
import com.survivalcoding.noteapp.domain.model.NoteItem
import com.survivalcoding.noteapp.domain.usecase.DeleteNoteUseCase
import com.survivalcoding.noteapp.domain.usecase.GetAllNotesUseCase
import com.survivalcoding.noteapp.domain.usecase.UpsertNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class NoteListViewModel(
  private val getAllNotesUseCase: GetAllNotesUseCase,
  private val deleteNoteUseCase: DeleteNoteUseCase,
  private val upsertNoteUseCase: UpsertNoteUseCase
) : ViewModel() {
  private val notes by lazy { MutableStateFlow(getNotes()) }

  fun getNotes() = getAllNotesUseCase()
  suspend fun deleteNote(noteItem: NoteItem) = deleteNoteUseCase(noteItem)
  suspend fun upsertNote(noteItem: NoteItem) = upsertNoteUseCase(noteItem)
}
