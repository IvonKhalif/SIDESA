package com.gov.sidesa.utils

import android.view.View

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