package com.survivalcoding.noteapp.presentation.add_edit_note.adapter

import androidx.recyclerview.widget.DiffUtil

object ColorDiffItemCallback : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }
}