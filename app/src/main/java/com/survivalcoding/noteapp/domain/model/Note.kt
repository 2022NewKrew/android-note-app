package com.survivalcoding.noteapp.domain.model

import java.util.*

data class Note(
    val id: Int = 0,
    val title: String,
    val content: String,
    val timestamp: Long = Date().time,
    val color: Int,
)