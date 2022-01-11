package com.survivalcoding.noteapp.presentation.notes.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.NoteListItemBinding
import com.survivalcoding.noteapp.domain.model.Note

class NoteItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false)
) {
    private val binding = NoteListItemBinding.bind(itemView)

    fun binding(
        currentNote: Note,
        deleteClickEvent: (note: Note) -> Unit,
        itemClickEvent: (note: Note) -> Unit,
    ) {
        binding.tvNoteTitle.text = currentNote.title
        binding.tvNoteContent.text = currentNote.content
        binding.cvItem.setCardBackgroundColor(Color.parseColor(currentNote.color))

        itemView.setOnClickListener {
            itemClickEvent(currentNote)
        }

        binding.ivDeleteButton.setOnClickListener {
            deleteClickEvent(currentNote)
        }
    }
}