package com.survivalcoding.noteapp.presentation

import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.domain.model.Color

fun color2ColorResourceId(color: Color) = when (color) {
    Color.YELLOW -> R.color.yellow
    Color.BLUE -> R.color.blue
    Color.LAVENDER -> R.color.lavender
    Color.PURPLE -> R.color.purple
    Color.RED -> R.color.red
}