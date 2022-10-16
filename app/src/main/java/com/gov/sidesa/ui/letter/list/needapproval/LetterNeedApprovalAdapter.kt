package com.gov.sidesa.ui.letter.list.needapproval

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.domain.letter.list.models.LetterListApprovalModel

class LetterNeedApprovalAdapter(
    _items: List<LetterListApprovalModel>,
    private val onItemClick: (LetterListApprovalModel) -> Unit
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