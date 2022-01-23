package com.survivalcoding.noteapp.notes.adapter

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.domain.entity.Note
import com.survivalcoding.noteapp.MyColors
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.ItemNoteBinding

class NoteViewHolder(private val binding: ItemNoteBinding) : ViewHolder(binding.root) {

    fun bind(
        item: Note,
        onLongClicked: (Note) -> Unit,

        ) {
        binding.noteTitleView.text = item.title
        binding.noteContentView.text = item.content

        val background = binding.root.background  as LayerDrawable
        val rectangleFrame =  background.findDrawableByLayerId(R.id.rectangle_frame) as GradientDrawable
        rectangleFrame.setColor(item.color)

        binding.root.setOnLongClickListener {
            onLongClicked(item)
            true
        }
    }

    companion object {
        fun builder(parent: ViewGroup): NoteViewHolder =
            NoteViewHolder(
                ItemNoteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }


}

