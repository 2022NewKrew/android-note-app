package com.survivalcoding.noteapp.presentation.notes.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.ItemNoteBinding
import com.survivalcoding.noteapp.domain.model.Note
import kotlin.math.roundToInt


class NoteViewHolder(
    itemView: View,
    val onClickDeleteButton: (Note) -> Unit,
    val onClickView: (Note) -> Unit,
) :
    RecyclerView.ViewHolder(itemView) {
    private val binding = ItemNoteBinding.bind(itemView)

    fun bind(note: Note) {
        binding.title.text = note.title
        binding.content.text = note.content
        val layers = binding.root.background as LayerDrawable
        val mainNote = layers.findDrawableByLayerId(R.id.main) as GradientDrawable
        mainNote.setColor(note.color)
        val foldNote = layers.findDrawableByLayerId(R.id.fold) as GradientDrawable
        foldNote.setColor(getDarkColor(note.color))

        itemView.setOnClickListener {
            onClickView(note)
        }

        binding.deleteButton.setOnClickListener {
            onClickDeleteButton(note)
        }
    }

    private fun getDarkColor(color: Int): Int {
        val factor = 0.8
        val a: Int = Color.alpha(color)
        val r = (Color.red(color) * factor).roundToInt()
        val g = (Color.green(color) * factor).roundToInt()
        val b = (Color.blue(color) * factor).roundToInt()
        return Color.argb(
            a,
            r.coerceAtMost(255),
            g.coerceAtMost(255),
            b.coerceAtMost(255)
        )
    }
}