package com.survivalcoding.noteapp.presentation.add_edit_note.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.noteapp.domain.model.ColorItem

class ColorListAdapter(
    private val itemClickEvent: (colorItem: ColorItem) -> Unit,
) : ListAdapter<ColorItem, ColorItemViewHolder>(ColorDiffUtilCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorItemViewHolder {
        return ColorItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ColorItemViewHolder, position: Int) {
        holder.binding(getItem(position), itemClickEvent)
    }
}