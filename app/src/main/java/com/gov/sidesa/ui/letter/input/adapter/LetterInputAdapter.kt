package com.gov.sidesa.ui.letter.input.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.gov.sidesa.base.dynamic_adapter.AbstractViewHolder
import com.gov.sidesa.base.dynamic_adapter.DiffUtilItemCallback
import com.gov.sidesa.ui.letter.input.models.base.BaseLetterInputModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactory
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderListener

@Suppress("UNCHECKED_CAST")
class LetterInputAdapter(
    private val viewHolderFactory: LetterInputViewHolderFactory,
    private val listener: LetterInputViewHolderListener
): ListAdapter<BaseLetterInputModel, AbstractViewHolder<BaseLetterInputModel>>(
    DiffUtilItemCallback(
        isItemsTheSame = { oldItem, newItem ->  oldItem.areItemsTheSame(newItem) },
        isContentTheSame = { oldItem, newItem ->  oldItem.areContentsTheSame(newItem) }
    )
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<BaseLetterInputModel> {
        return viewHolderFactory.createViewHolder(
            parent = parent,
            type = viewType,
            listener = listener
        ) as AbstractViewHolder<BaseLetterInputModel>
    }

    override fun onBindViewHolder(holder: AbstractViewHolder<BaseLetterInputModel>, position: Int) {
        val a = getItem(position)
        holder.bind(a)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type(viewHolderFactory)
    }
}