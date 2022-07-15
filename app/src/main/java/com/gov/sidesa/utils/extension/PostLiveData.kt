package com.gov.sidesa.utils.extension

import androidx.lifecycle.LifecycleOwner
import com.gov.sidesa.utils.PostLiveData

fun <T> PostLiveData<T?>.observeNonNull(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner) {
        it?.let {
            observer(it)
        }
    }
}