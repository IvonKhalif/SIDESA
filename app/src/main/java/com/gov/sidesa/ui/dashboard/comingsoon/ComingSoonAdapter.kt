package com.gov.sidesa.ui.dashboard.comingsoon

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.domain.comingsoon.models.ComingSoonModel

class ComingSoonAdapter(
    _items: List<ComingSoonModel>
): RecyclerView.Adapter<ComingSoonViewHolder>() {
    var items = _items
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComingSoonViewHolder {
        return ComingSoonViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ComingSoonViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}