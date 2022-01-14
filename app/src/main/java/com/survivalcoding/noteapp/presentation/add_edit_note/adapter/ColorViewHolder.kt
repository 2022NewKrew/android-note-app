package com.survivalcoding.noteapp.presentation.add_edit_note.adapter

import android.content.res.ColorStateList
import android.view.View
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.noteapp.databinding.ItemColorBinding


class ColorViewHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemColorBinding.bind(itemView)

    fun bind(color: Int) {
        binding.radioButton.buttonTintList =
            ColorStateList.valueOf(ContextCompat.getColor(itemView.context, color))
    }

    fun getRadioButton(): RadioButton = binding.radioButton
}