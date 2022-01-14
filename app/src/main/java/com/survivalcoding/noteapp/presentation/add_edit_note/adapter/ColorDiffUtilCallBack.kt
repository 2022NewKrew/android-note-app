package com.survivalcoding.noteapp.presentation.add_edit_note.adapter

import androidx.recyclerview.widget.DiffUtil
import com.survivalcoding.noteapp.domain.model.ColorItem

object ColorDiffUtilCallBack : DiffUtil.ItemCallback<ColorItem>() {
    override fun areItemsTheSame(oldItem: ColorItem, newItem: ColorItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ColorItem, newItem: ColorItem): Boolean {
        return oldItem == newItem
    }

}