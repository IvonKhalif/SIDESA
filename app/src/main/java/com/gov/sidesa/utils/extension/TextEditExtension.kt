package com.gov.sidesa.utils.extension

import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener

/**
 * Created by yovi.putra on 01/08/22"
 * Project name: SIDESA
 **/
 
fun EditText.setTextDistinct(value: String) {
    if (value != text.toString()) {
        setText(value)
    }
}


fun TextView.distinctTextChange(old: String = "", onTextChanged: (String) -> Unit = {}) {
    var prev = ""

    addTextChangedListener(onTextChanged = { _, _, _, _ ->
        synchronized(prev) {
            if (prev != text.toString()) {
                prev = text.toString()
                onTextChanged.invoke(this.text.toString())
            }
        }
    })
}