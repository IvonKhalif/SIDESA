package com.example.containertracker.ui.history

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.containertracker.data.history.model.HistoryModel

class HistoryAdapter(
    _items: List<HistoryModel>,
    private val isHistoryDetail: Boolean = false,
    private val onItemClick: (HistoryModel) -> Unit
): RecyclerView.Adapter<HistoryViewHolder>() {
    var items = _items
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder.create(parent, isHistoryDetail, onItemClick)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}