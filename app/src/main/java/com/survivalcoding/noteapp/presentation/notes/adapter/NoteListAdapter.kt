package com.survivalcoding.noteapp.presentation.notes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.domain.model.Note

class NoteListAdapter(
    private val onClickDeleteButton: (Note) -> Unit,
    private val onClickView: (Note) -> Unit,
) : ListAdapter<Note, NoteViewHolder>(NoteDiffItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view, onClickDeleteButton, onClickView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}