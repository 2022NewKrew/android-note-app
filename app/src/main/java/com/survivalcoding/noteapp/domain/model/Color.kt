package com.survivalcoding.noteapp.domain.model

enum class Color(val value: Int) {
    ORANGE(0),
    YELLOW(1),
    PURPLE(2),
    BLUE(3),
    PINK(4);

    companion object {
        val defaultColor = ORANGE
    }
}