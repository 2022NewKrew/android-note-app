package com.survivalcoding.noteapp.presentation.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.noteapp.databinding.NoteListItemBinding
import com.survivalcoding.noteapp.domain.model.Note

class NoteViewHolder(private val binding: NoteListItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(note: Note) {
        binding.titleTextView.text = note.title
        binding.contentTextView.text = note.content
    }

    companion object {
        fun from(parent: ViewGroup): NoteViewHolder {
            return NoteViewHolder(
                NoteListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}