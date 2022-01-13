package com.survivalcoding.noteapp.presentation.notes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.noteapp.databinding.ItemNoteBinding
import com.survivalcoding.noteapp.domain.model.Note

class NotesAdapter(private val onClick: (Note) -> Unit, private val onDelete: (Note) -> Unit) :
    ListAdapter<Note, NoteViewHolder>(NoteDiffItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position), onClick, onDelete)
    }
}