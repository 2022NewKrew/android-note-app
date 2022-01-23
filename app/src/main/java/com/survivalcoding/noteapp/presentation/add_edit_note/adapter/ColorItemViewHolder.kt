package com.survivalcoding.noteapp.presentation.add_edit_note.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.ColorListItemBinding
import com.survivalcoding.noteapp.domain.model.ColorItem
import com.survivalcoding.noteapp.domain.model.NoteColor

class ColorItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.color_list_item, parent, false)
) {
    private val binding = ColorListItemBinding.bind(itemView)

    fun binding(
        colorItem: ColorItem,
        itemClickEvent: (colorItem: ColorItem) -> Unit,
    ) {
        binding.colorItem.setImageResource(
            if (colorItem.isChecked) R.drawable.circle_selected
            else R.drawable.circle_not_selected
        )
        binding.colorItem.setBackgroundColor(
            when (colorItem.id) {
                0L -> NoteColor.COLOR_1.toInt()
                1L -> NoteColor.COLOR_2.toInt()
                2L -> NoteColor.COLOR_3.toInt()
                3L -> NoteColor.COLOR_4.toInt()
                4L -> NoteColor.COLOR_5.toInt()
                else -> NoteColor.COLOR_1.toInt()
            }
        )

        binding.colorItem.setOnClickListener {
            itemClickEvent(colorItem)
        }
    }
}