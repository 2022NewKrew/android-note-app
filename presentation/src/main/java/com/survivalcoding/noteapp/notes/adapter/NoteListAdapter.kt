package com.survivalcoding.noteapp.notes.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.entity.Note

class NoteListAdapter(
    private val onLongClicked: (Note) -> Unit,
    private val onLeftSwiped: (Note) -> Unit
) :
    ListAdapter<Note, NoteViewHolder>(NoteDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        NoteViewHolder.builder(parent)

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position), onLongClicked)
    }

    fun removeItem(position: Int) {
        if (position >= itemCount) return
        onLeftSwiped(currentList.toMutableList()[position])
    }
}