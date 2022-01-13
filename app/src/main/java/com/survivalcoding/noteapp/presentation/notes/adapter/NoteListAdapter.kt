package com.survivalcoding.noteapp.presentation.notes.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.presentation.notes.UiState

class NoteListAdapter(
    private val deleteClickEvent: (note: Note) -> Unit,
    private val itemClickEvent: (note: Note) -> Unit,
) : ListAdapter<Note, NoteItemViewHolder>(DiffUtilCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemViewHolder {
        return NoteItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        holder.binding(getItem(position), deleteClickEvent, itemClickEvent)
    }

    fun submitSortedList(list: List<Note> = currentList, uiState: UiState) {
        val tList = when (uiState.sortMode) {
            UiState.ORDER_ASC -> {
                when (uiState.sortKey) {
                    UiState.ORDER_TITLE -> list.sortedBy { it.title }
                    UiState.ORDER_TIMESTAMP -> list.sortedBy { it.timestamp }
                    else -> list.sortedBy { it.color }
                }
            }
            else -> {
                when (uiState.sortKey) {
                    UiState.ORDER_TITLE -> list.sortedByDescending { it.title }
                    UiState.ORDER_TIMESTAMP -> list.sortedByDescending { it.timestamp }
                    else -> list.sortedByDescending { it.color }
                }
            }
        }
        super.submitList(tList)
    }
}