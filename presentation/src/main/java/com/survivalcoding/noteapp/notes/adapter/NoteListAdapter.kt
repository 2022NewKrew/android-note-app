package com.survivalcoding.noteapp.notes.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.entity.Note

class NoteListAdapter(
    private val onLongClicked: (Note) -> Unit,
    private val onLeftSwiped: (Note) -> Unit
) :
    ListAdapter<Note, NoteViewHolder>(NoteDiffItemCallback) {

    private var tmpItem: Note? = null
    private var tmpPosition: Int? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        NoteViewHolder.builder(parent)

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position), onLongClicked)
    }

    fun removeItem(position: Int) {
        if (position >= itemCount) return
        tmpPosition = position
        tmpItem = currentList[position]
        onLeftSwiped(currentList[position])
        submitList(currentList.toMutableList().filter {
            it.id != tmpItem!!.id
        })
    }

    fun unDoDelete() {
        if (tmpPosition != null && tmpItem != null) {
            submitList(currentList.toMutableList().apply {
                add(tmpPosition!!, tmpItem)
            })
            tmpPosition = null
            tmpItem = null
        }
    }

    fun sorting(sorted_with: String, ascOrDesc: String) {
        when (ascOrDesc) {
            "ASC" -> {
                if (sorted_with == "TIME") submitList(currentList.sortedBy { it.timestamp })
                else if (sorted_with == "TITLE") submitList(currentList.sortedBy { it.title })
                else if (sorted_with == "COLOR") submitList(currentList.sortedBy { it.color })
            }
            "DESC" -> {
                if (sorted_with == "TIME") submitList(currentList.sortedByDescending { it.timestamp })
                else if (sorted_with == "TITLE") submitList(currentList.sortedByDescending { it.title })
                else if (sorted_with == "COLOR") submitList(currentList.sortedByDescending { it.color })
            }
        }
    }
}