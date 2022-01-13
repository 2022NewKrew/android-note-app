package com.survivalcoding.noteapp.domain.model

import android.graphics.Color
import com.survivalcoding.noteapp.R

enum class NoteColor {
    COLOR_1, COLOR_2, COLOR_3, COLOR_4, COLOR_5;

    fun toInt(): Int {
        return when (this) {
            COLOR_1 -> Color.parseColor("#ffa288")
            COLOR_2 -> Color.parseColor("#e3ec96")
            COLOR_3 -> Color.parseColor("#c886d2")
            COLOR_4 -> Color.parseColor("#76d9e6")
            COLOR_5 -> Color.parseColor("#f282a5")
        }
    }

    fun toId(): Int {
        return when (this) {
            COLOR_1 -> R.id.rb_color1
            COLOR_2 -> R.id.rb_color2
            COLOR_3 -> R.id.rb_color3
            COLOR_4 -> R.id.rb_color4
            COLOR_5 -> R.id.rb_color5
        }
    }
}