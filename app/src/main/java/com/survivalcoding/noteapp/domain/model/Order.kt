package com.survivalcoding.noteapp.domain.model

enum class Order {
    TITLE_ASC,
    TITLE_DESC,
    DATE_ASC,
    DATE_DESC,
    COLOR_ASC,
    COLOR_DSC;

    companion object {
        val defaultOrder get() = TITLE_ASC
    }
}