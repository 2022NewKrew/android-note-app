package com.survivalcoding.noteapp

import android.view.View

fun View.toggleVisibility() {
    visibility = if(visibility == View.VISIBLE) View.GONE else View.VISIBLE
}