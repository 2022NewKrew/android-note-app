package com.survivalcoding.noteapp.domain.model

import com.survivalcoding.noteapp.R

enum class Color(private val resId: Int) {
    ORANGE(R.color.orange),
    YELLOW(R.color.yellow),
    PURPLE(R.color.purple),
    BLUE(R.color.blue),
    PINK(R.color.pink);

    fun resId(): Int = resId
}