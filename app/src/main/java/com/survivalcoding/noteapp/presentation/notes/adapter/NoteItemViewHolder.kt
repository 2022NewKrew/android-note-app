package com.survivalcoding.noteapp.presentation.notes.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.NoteListItemBinding
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.presentation.add_edit_note.AddEditNoteFragment
import com.survivalcoding.noteapp.presentation.add_edit_note.AddEditNoteViewModel

class NoteItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false)
) {
    private val binding = NoteListItemBinding.bind(itemView)

    fun binding(
        currentNote: Note,
        clickEvent: (note: Note) -> Unit,
    ) {
        binding.tvNoteTitle.text = currentNote.title
        binding.tvNoteContent.text = currentNote.content
        binding.cvItem.setBackgroundColor(Color.parseColor(currentNote.color))

        binding.ivDeleteButton.setOnClickListener {
            clickEvent(currentNote)
        }
    }
}