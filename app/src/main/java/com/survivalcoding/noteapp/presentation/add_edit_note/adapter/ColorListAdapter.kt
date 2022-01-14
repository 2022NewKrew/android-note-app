package com.survivalcoding.noteapp.presentation.add_edit_note.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.noteapp.R

class ColorListAdapter(
    private val onColorClicked: (Int) -> Unit,
) : ListAdapter<Int, ColorViewHolder>(ColorDiffItemCallback) {
    private var lastCheckedRB: RadioButton? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(getItem(position))

        val radioButton = holder.getRadioButton()
        radioButton.setOnClickListener {
            lastCheckedRB?.let {
                if (lastCheckedRB != radioButton) it.isChecked = false
            }
            lastCheckedRB = radioButton
            onColorClicked(getItem(position))
        }
    }
}