package com.survivalcoding.noteapp.presentation

import android.content.Context
import androidx.core.content.ContextCompat
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.domain.model.Color

fun color2Id(color: Color) = when (color) {
    Color.ORANGE -> R.color.orange
    Color.BLUE -> R.color.blue
    Color.YELLOW -> R.color.yellow
    Color.PURPLE -> R.color.purple
    Color.PINK -> R.color.pink
}

fun id2ColorInt(context: Context, id: Int) = ContextCompat.getColor(context, id)