package com.survivalcoding.note_app.presentation.notes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.note_app.databinding.ItemNoteBinding
import com.survivalcoding.note_app.domain.model.Note

class NoteListAdapter(
    val onDelete: (note: Note) -> Unit
) : ListAdapter<Note, NoteListAdapter.NoteViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = getItem(position)
        holder.binding.apply {
            titleTextView.text = currentNote.title
            contentTextView.text = currentNote.content
            root.setBackgroundColor(currentNote.color)
            deleteImageView.setOnClickListener {
                onDelete(currentNote)
            }
        }
    }

    class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    object DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}


