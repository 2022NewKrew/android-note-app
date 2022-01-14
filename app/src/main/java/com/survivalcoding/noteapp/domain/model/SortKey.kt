package com.survivalcoding.noteapp.domain.model

import com.survivalcoding.noteapp.R

enum class SortKey {
    TITLE, TIMESTAMP, COLOR;

    fun toComparator(): Comparator<Note> {
        return when (this) {
            TITLE -> compareBy { it.title }
            TIMESTAMP -> compareBy { it.timestamp }
            COLOR -> compareBy { it.color }
        }
    }

    fun toId(): Int {
        return when (this) {
            TITLE -> R.id.rb_base_title
            TIMESTAMP -> R.id.rb_base_date
            COLOR -> R.id.rb_base_color
        }
    }
}