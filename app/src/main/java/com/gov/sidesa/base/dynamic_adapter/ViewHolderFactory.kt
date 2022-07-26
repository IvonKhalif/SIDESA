package com.gov.sidesa.base.dynamic_adapter

import android.view.View

interface ViewHolderFactory {

    fun createViewHolder(parent: View, type: Int): AbstractViewHolder<Any>
}