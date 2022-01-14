package com.survivalcoding.noteapp.presentation.notes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.noteapp.databinding.ItemNoteBinding
import com.survivalcoding.noteapp.domain.model.Note

class NoteListAdapter(
    private val deleteClickListener: (Note) -> Unit,
    private val itemClickListener: (Note) -> Unit
) :
    ListAdapter<Note, NoteViewHolder>(NoteDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), deleteClickListener, itemClickListener
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }
}