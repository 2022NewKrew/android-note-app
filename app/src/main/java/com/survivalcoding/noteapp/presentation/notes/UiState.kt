package com.survivalcoding.noteapp.presentation.notes

import android.view.View

data class UiState(
    val sortKey: String = ORDER_TITLE,
    val sortMode: Int = ORDER_ASC,
    val sortVisible: Int = FILTER_CLOSE,
) {
    companion object {
        const val ORDER_TITLE = "title"
        const val ORDER_COLOR = "color"
        const val ORDER_TIMESTAMP = "timestamp"

        const val ORDER_ASC = 1
        const val ORDER_DESC = 0

        const val FILTER_OPEN = View.VISIBLE
        const val FILTER_CLOSE = View.GONE
    }
}