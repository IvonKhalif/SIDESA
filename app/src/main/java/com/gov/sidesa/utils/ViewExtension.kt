package com.gov.sidesa.utils

import android.content.res.Resources
import android.view.View
import kotlin.math.roundToInt

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.isVisible(value: Boolean) {
    if (value) {
        visible()
    } else {
        gone()
    }
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()