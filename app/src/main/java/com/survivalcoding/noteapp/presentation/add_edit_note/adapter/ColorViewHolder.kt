package com.survivalcoding.noteapp.presentation.add_edit_note.adapter

import android.graphics.Color
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.noteapp.databinding.ItemColorBinding
import com.survivalcoding.noteapp.presentation.id2ColorInt
import kotlin.math.roundToInt

class ColorViewHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemColorBinding.bind(itemView)

    fun bind(color: Int) {
        val gradientDrawable = binding.radioButton.buttonDrawable as StateListDrawable
        val drawableContainerState =
            gradientDrawable.constantState as DrawableContainer.DrawableContainerState
        val children = drawableContainerState.children

        val colorInt = id2ColorInt(itemView.context, color)
        val selectedItem = children[0] as GradientDrawable
        selectedItem.setColor(colorInt)
        selectedItem.setStroke(10, Color.BLACK)

        val unselectedItem = children[1] as GradientDrawable
        unselectedItem.setColor(colorInt)
        unselectedItem.setStroke(10, getDarkColor(colorInt))
    }

    private fun getDarkColor(color: Int): Int {
        val factor = 0.8
        val a: Int = Color.alpha(color)
        val r = (Color.red(color) * factor).roundToInt()
        val g = (Color.green(color) * factor).roundToInt()
        val b = (Color.blue(color) * factor).roundToInt()
        return Color.argb(
            a,
            r.coerceAtMost(255),
            g.coerceAtMost(255),
            b.coerceAtMost(255)
        )
    }

    fun getRadioButton(): RadioButton = binding.radioButton
}