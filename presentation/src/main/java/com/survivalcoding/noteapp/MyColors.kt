package com.survivalcoding.noteapp

enum class MyColors(var rgb: Int) {
    Blue(0x33b5e5),
    Purple(0xBB86FC),
    Green(0x99cc00);

    fun getColor() = 0xff shl 24 or this.rgb


}