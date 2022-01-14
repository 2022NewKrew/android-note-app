package com.survivalcoding.noteapp.presentation.note.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.survivalcoding.noteapp.domain.model.NoteItem

class NoteDiffCallback : DiffUtil.ItemCallback<NoteItem>() {
  override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean = oldItem.id == newItem.id

  override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean = oldItem == newItem
}
