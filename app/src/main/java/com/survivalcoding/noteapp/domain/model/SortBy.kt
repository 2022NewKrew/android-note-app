package com.survivalcoding.noteapp.domain.model

enum class SortBy(val value: Int) {
    BY_TITLE(0),
    BY_DATE(1),
    BY_COLOR(2);

    companion object {
        val default = BY_DATE
    }
}