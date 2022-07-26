package com.gov.sidesa.ui.letterlist.needapproval

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.data.letterlist.models.LettersModel

class LetterNeedApprovalAdapter(
    _items: List<LettersModel>,
    private val onItemClick: (LettersModel) -> Unit
): RecyclerView.Adapter<LetterNeedApprovalViewHolder>() {
    var items = _items
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterNeedApprovalViewHolder {
        return LetterNeedApprovalViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: LetterNeedApprovalViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}