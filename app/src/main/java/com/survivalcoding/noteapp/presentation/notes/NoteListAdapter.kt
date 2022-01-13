package com.survivalcoding.noteapp.presentation.notes

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.noteapp.domain.model.Note

class NoteListAdapter (private val onDeleteButtonClick: (Note) -> Unit) : ListAdapter<Note, NoteViewHolder>(NoteDiffUtilItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteViewHolder.from(parent)

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(currentList[position], onDeleteButtonClick)
    }
}