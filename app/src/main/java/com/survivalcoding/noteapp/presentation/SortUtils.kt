package com.survivalcoding.noteapp.presentation

import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.domain.model.Order
import com.survivalcoding.noteapp.domain.model.SortBy

fun id2Order(id: Int): Order = when (id) {
    R.id.ascendingButton -> Order.ASC
    R.id.descendingButton -> Order.DESC
    else -> Order.default
}

fun id2SortBy(id: Int): SortBy = when (id) {
    R.id.titleButton -> SortBy.BY_TITLE
    R.id.dateButton -> SortBy.BY_DATE
    R.id.colorButton -> SortBy.BY_COLOR
    else -> SortBy.default
}

fun order2Id(order: Order): Int = when (order) {
    Order.ASC -> R.id.ascendingButton
    Order.DESC -> R.id.descendingButton
}

fun sortBy2Id(sortBy: SortBy): Int = when (sortBy) {
    SortBy.BY_TITLE -> R.id.titleButton
    SortBy.BY_DATE -> R.id.dateButton
    SortBy.BY_COLOR -> R.id.colorButton
}
