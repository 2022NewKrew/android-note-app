package com.survivalcoding.noteapp.domain.model

enum class Order {
    TITLE_ASC,
    TITLE_DESC,
    DATE_ASC,
    DATE_DESC,
    COLOR_ASC,
    COLOR_DESC;

    companion object {
        val defaultOrder get() = TITLE_ASC

        fun isAsc(order: Order) = when(order) {
            TITLE_ASC, DATE_ASC, COLOR_ASC -> true
            else -> false
        }
    }
}