package com.gov.sidesa.utils.picker

import androidx.recyclerview.widget.DiffUtil

class DiffUtilItemCallback<T>(
    private val isItemsTheSame: (oldItem: T, newItem: T) -> Boolean,
    private val isContentTheSame: (oldItem: T, newItem: T) -> Boolean
) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return isItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return isContentTheSame(oldItem, newItem)
    }
}