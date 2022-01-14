package com.survivalcoding.noteapp.notes.adapter

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
        binding.cardView.setBackgroundColor(item.color)
        /* 날짜 지정
        val pattern = "yyyy-MM-dd HH:mm:ss"
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        val date = "set : " + formatter.format(item.timestamp)
        binding.deadlineTextview.text = date
         */
        binding.root.setOnLongClickListener() {
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

