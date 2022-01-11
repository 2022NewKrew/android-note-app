package com.survivalcoding.noteapp.presentation.notes.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.noteapp.domain.model.Note

class NoteListAdapter(
    private val clickEvent: (note: Note) -> Unit,
) : ListAdapter<Note, NoteItemViewHolder>(DiffUtilCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemViewHolder {
        return NoteItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        holder.binding(getItem(position), clickEvent)
    }
}