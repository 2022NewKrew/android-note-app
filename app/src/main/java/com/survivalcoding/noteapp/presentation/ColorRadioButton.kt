package com.survivalcoding.noteapp.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.survivalcoding.noteapp.R

class ColorRadioButton(context: Context, attributeSet: AttributeSet) :
    androidx.appcompat.widget.AppCompatRadioButton(context, attributeSet) {

    var color = Color.WHITE
        private set

    init {
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.ColorRadioButton,
            0, 0
        ).apply {
            try {
                color = getInteger(R.styleable.ColorRadioButton_colorValue, ContextCompat.getColor(context, R.color.light_salmon))
            } finally {
                recycle()
            }

            background = ContextCompat.getDrawable(context, R.drawable.selector_circle)
            backgroundTintMode = PorterDuff.Mode.MULTIPLY
            background.setTint(color)
            buttonDrawable = null
        }
    }
}