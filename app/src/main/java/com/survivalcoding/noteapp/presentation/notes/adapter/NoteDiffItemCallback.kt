package com.survivalcoding.noteapp.presentation.notes.adapter

import androidx.recyclerview.widget.DiffUtil
import com.survivalcoding.noteapp.domain.model.Note

object NoteDiffItemCallback: DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}