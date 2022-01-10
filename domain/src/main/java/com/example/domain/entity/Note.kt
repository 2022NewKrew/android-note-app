package com.example.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "NOTE_TB")
data class Note(
    @PrimaryKey val id: Long,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
)