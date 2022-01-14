package com.survivalcoding.noteapp.presentation.notes.adapter

import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.noteapp.databinding.ItemNoteBinding
import com.survivalcoding.noteapp.domain.model.Note

class NoteViewHolder(
    private val binding: ItemNoteBinding,
    private val deleteClickListener: (Note) -> Unit,
    private val itemClickListener: (Note) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(note: Note) {
        binding.noteTvTitle.text = note.title
        binding.noteTvContent.text = note.content
        binding.root.setBackgroundColor(
            binding.root.context.resources.getColor(
                note.color,
                binding.root.context.theme
            )
        )

        binding.noteIvDelete.setOnClickListener { deleteClickListener(note) }
        binding.root.setOnClickListener { itemClickListener(note) }
    }
}