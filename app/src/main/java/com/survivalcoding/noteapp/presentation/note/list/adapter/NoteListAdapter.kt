package com.survivalcoding.noteapp.presentation.note.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.noteapp.databinding.NoteListItemBinding
import com.survivalcoding.noteapp.domain.model.NoteItem
import com.survivalcoding.noteapp.presentation.note.list.NoteItemViewHolder

class NoteListAdapter : ListAdapter<NoteItem, NoteItemViewHolder>(NoteDiffCallback()) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemViewHolder {
    return NoteItemViewHolder(
      NoteListItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
    holder.bind(getItem(position))
  }
}
