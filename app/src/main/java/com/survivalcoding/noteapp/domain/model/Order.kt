package com.survivalcoding.noteapp.domain.model

enum class Order(val value: Int) {
    ASC(0),
    DESC(1);

    companion object {
        val default = ASC
    }
}