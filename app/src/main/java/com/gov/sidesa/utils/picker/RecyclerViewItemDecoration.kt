package com.gov.sidesa.utils.picker

import android.graphics.Canvas
import android.graphics.PorterDuff
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.R

class RecyclerViewItemDecoration(
    private val marginHorizontal: Int = 0,
    private val height: Int = 0,
    private val color: Int? = null
) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        drawVerticalDividers(c, parent)
    }

    private fun drawVerticalDividers(
        c: Canvas,
        parent: RecyclerView
    ) {
        // divider to be drawn below of parent's item
        val drawable = ContextCompat.getDrawable(parent.context, R.drawable.divider) ?: return

        if (color != null) {
            drawable.setTintMode(PorterDuff.Mode.DST)
            drawable.setTint(color)
        }

        // divider bound
        val leftBound = parent.left + marginHorizontal
        val rightBound = parent.right - marginHorizontal
        // draw divider
        for (i in 0 until parent.childCount) {
            val child: View = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val topBound: Int = child.bottom + params.bottomMargin
            val bottomBound: Int = topBound + drawable.intrinsicHeight + height
            drawable.setBounds(leftBound, topBound, rightBound, bottomBound)
            drawable.draw(c)
        }
    }
}