package com.survivalcoding.noteapp.domain

enum class NoteOrderBy(val columnName: String) {
    TITLE("title"),
    TIMESTAMP("timestamp"),
    COLOR("color");
}