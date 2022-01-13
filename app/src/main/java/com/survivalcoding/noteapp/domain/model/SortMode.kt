package com.survivalcoding.noteapp.domain.model

import com.survivalcoding.noteapp.R

enum class SortMode {
    ASCENDING, DESCENDING;

    fun toId(): Int {
        return when (this) {
            ASCENDING -> R.id.rb_mode_asc
            DESCENDING -> R.id.rb_mode_desc
        }
    }
}