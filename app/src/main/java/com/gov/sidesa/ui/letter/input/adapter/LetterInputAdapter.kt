package com.gov.sidesa.ui.letter.input.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.gov.sidesa.base.dynamic_adapter.AbstractViewHolder
import com.gov.sidesa.base.dynamic_adapter.BaseItemModel
import com.gov.sidesa.base.dynamic_adapter.DiffUtilItemCallback
import com.gov.sidesa.base.dynamic_adapter.ViewHolderFactory

class LetterInputAdapter(
    private val adapterTypeFactory: ViewHolderFactory,
) : ListAdapter<BaseItemModel, AbstractViewHolder<BaseItemModel>>(
    DiffUtilItemCallback(
        isItemsTheSame = { oldItem, newItem -> oldItem.areItemsTheSame(newItem) },
        isContentTheSame = { oldItem, newItem -> oldItem.areContentsTheSame(newItem) }
    )
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractViewHolder<BaseItemModel> {
        val view = inflateItem(parent, viewType)
        return adapterTypeFactory.createViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: AbstractViewHolder<BaseItemModel>, position: Int) {
        holder.bind(getItem(position))
    }

    private fun inflateItem(viewGroup: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(viewGroup.context).inflate(viewType, viewGroup, false)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type(adapterTypeFactory)
    }
}