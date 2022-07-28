package com.gov.sidesa.utils.picker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.R
import com.gov.sidesa.databinding.ItemIconWithHeadlineArrowBinding

class MenuIconWithHeadlineAdapter<T>(
    private val setIconLeft: (T) -> Int,
    private val setHeadline: (T) -> String,
    private val setIconRight: (T) -> Int? = { null },
    private val customUi: ItemIconWithHeadlineArrowBinding.() -> Unit = {},
    private val onClick: (T) -> Unit,
    areItemsTheSame: (oldItem: T, newItem: T) -> Boolean,
    areContentTheSame: (oldItem: T, newItem: T) -> Boolean
) : ListAdapter<T, MenuIconWithHeadlineAdapter.ViewHolder<T>>(
    DiffUtilItemCallback(
        areItemsTheSame,
        areContentTheSame
    )
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        return ViewHolder.create(
            parent = parent,
            setHeadline = setHeadline,
            setIconLeft = setIconLeft,
            setIconRight = setIconRight,
            customUi = customUi,
            onClick = onClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.binding(getItem(position))
    }

    class ViewHolder<T>(
        private val view: ItemIconWithHeadlineArrowBinding,
        private val setIconLeft: (T) -> Int,
        private val setHeadline: (T) -> String,
        private val setIconRight: (T) -> Int?,
        private val customUi: ItemIconWithHeadlineArrowBinding.() -> Unit,
        private val onClick: (T) -> Unit
    ) : RecyclerView.ViewHolder(view.root) {

        fun binding(data: T) {
            val arrow = setIconRight.invoke(data) ?: R.drawable.ic_arrow_right
            view.ivArrow.setImageResource(arrow)
            view.ivIcon.setImageResource(setIconLeft.invoke(data))
            view.tvHeadline.text = setHeadline.invoke(data)
            customUi.invoke(view)
            view.root.setOnClickListener {
                onClick.invoke(data)
            }
        }

        companion object {

            fun <T> create(
                parent: ViewGroup,
                setIconLeft: (T) -> Int,
                setHeadline: (T) -> String,
                setIconRight: (T) -> Int?,
                customUi: ItemIconWithHeadlineArrowBinding.() -> Unit,
                onClick: (T) -> Unit
            ): ViewHolder<T> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemIconWithHeadlineArrowBinding.inflate(inflater, parent, false)
                return ViewHolder(
                    view = binding,
                    setHeadline = setHeadline,
                    setIconLeft = setIconLeft,
                    setIconRight = setIconRight,
                    customUi = customUi,
                    onClick = onClick
                )
            }
        }
    }
}