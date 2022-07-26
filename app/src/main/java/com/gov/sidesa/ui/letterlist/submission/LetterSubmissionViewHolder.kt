package com.gov.sidesa.ui.letterlist.submission

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.R
import com.gov.sidesa.data.letterlist.models.LettersModel
import com.gov.sidesa.databinding.ItemSubmissionBinding

class LetterSubmissionViewHolder(
    val binding: ItemSubmissionBinding,
    private val onItemClick: (LettersModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: LettersModel) {
        binding.apply {
            textLetterNumber.text = item.number
            textLetterTitle.text = item.title
            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onItemClicked: (LettersModel) -> Unit
        ): LetterSubmissionViewHolder {
            val view = DataBindingUtil
                .inflate<ItemSubmissionBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_submission,
                    parent,
                    false
                )
            return LetterSubmissionViewHolder(view, onItemClicked)
        }
    }
}