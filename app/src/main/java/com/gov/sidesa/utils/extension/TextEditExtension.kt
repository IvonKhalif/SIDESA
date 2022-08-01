package com.gov.sidesa.utils.extension

import android.widget.EditText

/**
 * Created by yovi.putra on 01/08/22"
 * Project name: SIDESA
 **/
 
fun EditText.setTextDistinct(value: String) {
    if (value != text.toString()) {
        setText(value)
    }
}