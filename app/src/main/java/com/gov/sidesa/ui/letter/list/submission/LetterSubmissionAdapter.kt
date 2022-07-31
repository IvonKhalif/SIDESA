package com.gov.sidesa.ui.letter.list.submission

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.domain.letter.list.models.LetterSubmissionModel

class LetterSubmissionAdapter(
    _items: List<LetterSubmissionModel>,
    private val onItemClick: (LetterSubmissionModel) -> Unit
): RecyclerView.Adapter<LetterSubmissionViewHolder>() {
    var items = _items
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterSubmissionViewHolder {
        return LetterSubmissionViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: LetterSubmissionViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}