package com.survivalcoding.noteapp.presentation.notes.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.NoteListItemBinding
import com.survivalcoding.noteapp.domain.model.Note
import kotlin.math.roundToInt

class NoteItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false)
) {
    private val binding = NoteListItemBinding.bind(itemView)

    fun binding(
        currentNote: Note,
        deleteClickEvent: (note: Note) -> Unit,
        itemClickEvent: (note: Note) -> Unit,
    ) {
        binding.tvNoteTitle.text = currentNote.title
        binding.tvNoteContent.text = currentNote.content

        val layers = binding.root.background as LayerDrawable
        val mainNote = layers.findDrawableByLayerId(R.id.main) as GradientDrawable
        val foldNote = layers.findDrawableByLayerId(R.id.fold) as GradientDrawable

        mainNote.setColor(currentNote.color.toInt())
        foldNote.setColor(getFoldedColor(currentNote.color.toInt()))

//        binding.cvItem.setCardBackgroundColor(currentNote.color.toInt())

        itemView.setOnClickListener {
            itemClickEvent(currentNote)
        }

        binding.ivDeleteButton.setOnClickListener {
            deleteClickEvent(currentNote)
        }
    }

    private fun getFoldedColor(color: Int): Int {
        val factor = 0.8

        val a: Int = Color.alpha(color)
        val r = (Color.red(color) * factor).roundToInt()
        val g = (Color.green(color) * factor).roundToInt()
        val b = (Color.blue(color) * factor).roundToInt()

        return Color.argb(
            a,
            r.coerceAtMost(255),
            g.coerceAtMost(255),
            b.coerceAtMost(255),
        )
    }
}