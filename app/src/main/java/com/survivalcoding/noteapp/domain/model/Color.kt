package com.survivalcoding.noteapp.domain.model

enum class Color(val value: Int) {
    RED(0),
    YELLOW(1),
    PURPLE(2),
    BLUE(3),
    LAVENDER(4);

    companion object {
        private val map = Color.values().associateBy(Color::value)
        fun fromInt(value: Int) = map[value]
    }
}
