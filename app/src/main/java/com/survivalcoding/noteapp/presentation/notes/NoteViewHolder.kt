package com.survivalcoding.noteapp.presentation.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.noteapp.databinding.NoteListItemBinding
import com.survivalcoding.noteapp.domain.model.Color
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.presentation.color2ColorResourceId

class NoteViewHolder(private val binding: NoteListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(note: Note, onNoteClick: (Note) -> Unit, onDeleteButtonClick: (Note) -> Unit) {
        binding.titleTextView.text = note.title
        binding.contentTextView.text = note.content
        binding.root.backgroundTintList = ResourcesCompat.getColorStateList(
            binding.root.resources,
            color2ColorResourceId(Color.fromInt(note.color)),
            null
        )
        binding.root.setOnClickListener { onNoteClick(note) }
        binding.deleteButton.setOnClickListener { onDeleteButtonClick(note) }
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