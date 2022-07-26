package com.gov.sidesa.ui.letterlist.submission

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.data.letterlist.models.LettersModel

class LetterSubmissionAdapter(
    _items: List<LettersModel>,
    private val onItemClick: (LettersModel) -> Unit
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