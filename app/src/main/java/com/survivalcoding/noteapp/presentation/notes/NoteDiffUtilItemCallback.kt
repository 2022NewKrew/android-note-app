package com.survivalcoding.noteapp.presentation.notes

import androidx.recyclerview.widget.DiffUtil
import com.survivalcoding.noteapp.domain.model.Note

class NoteDiffUtilItemCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem
}