package com.survivalcoding.noteapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Note(
    val id: Int = 0,
    val title: String,
    val content: String,
    val timestamp: Long = Date().time,
    val color: Int,
): Parcelable