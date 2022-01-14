package com.survivalcoding.noteapp.presentation.note.list

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.survivalcoding.noteapp.databinding.NoteListItemBinding
import com.survivalcoding.noteapp.domain.model.NoteItem

class NoteItemViewHolder(private val binding: NoteListItemBinding) : ViewHolder(binding.root) {
  fun bind(noteItem: NoteItem) {
    with(binding) {
      noteTitle.text = noteItem.title
      noteTimestamp.text = noteItem.timestamp.toString()
    }
  }
}
