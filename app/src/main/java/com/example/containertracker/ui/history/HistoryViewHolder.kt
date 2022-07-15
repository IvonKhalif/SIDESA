package com.example.containertracker.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.containertracker.R
import com.example.containertracker.data.history.model.HistoryModel
import com.example.containertracker.databinding.ItemHistoryBinding
import com.example.containertracker.utils.DateUtil
import com.example.containertracker.utils.DateUtil.convertLocalDateTimeToLocalDate
import com.example.containertracker.utils.DateUtil.convertPattern
import com.example.containertracker.utils.DateUtil.formatLocalDateTimeToString
import com.example.containertracker.utils.DateUtil.formatLocalDateToString
import com.example.containertracker.utils.DateUtil.formatStringToDateTime
import com.example.containertracker.utils.enums.ConditionEnum
import org.threeten.bp.LocalDateTime

class HistoryViewHolder(
    val binding: ItemHistoryBinding,
    private val isHistoryDetail: Boolean = false,
    private val onItemClick: (HistoryModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: HistoryModel) {
        binding.apply {
            val localDateTime = formatStringToDateTime(item.dateTime) ?: LocalDateTime.now()
            val date = convertLocalDateTimeToLocalDate(localDateTime)
            val dateFormat = if (isHistoryDetail) {
                val formatter = formatLocalDateTimeToString(localDateTime)
                convertPattern(formatter, DateUtil.LOCAL_DATE_TIME_PATTERN, "dd MMM yyyy HH:mm")
            } else {
                formatLocalDateToString(date, "dd MMM yyyy")
            }
            containerNameHistory.text = item.codeContainer
            containerNameHistory.isVisible = !isHistoryDetail
            postNameHistory.text = item.location
            textNameAccount.isVisible = isHistoryDetail
            textNameAccount.text = item.nameAccount
            dateHistory.text = dateFormat
            textSealNumber.text = if (item.sealId.isNullOrBlank()) "-" else item.sealId
            doorSideValue.text = item.frontDoorSideCondition
            backSideValue.text = item.backDoorSideCondition
            rightSideValue.text = item.rightSideCondition
            leftSideValue.text = item.leftSideCondition
            roofSideValue.text = item.roofSideCondition
            floorSideValue.text = item.floorSideCondition

            doorSideValue.setDrawableWithCondition(item.frontDoorSideCondition.orEmpty())
            backSideValue.setDrawableWithCondition(item.backDoorSideCondition.orEmpty())
            rightSideValue.setDrawableWithCondition(item.rightSideCondition.orEmpty())
            leftSideValue.setDrawableWithCondition(item.leftSideCondition.orEmpty())
            roofSideValue.setDrawableWithCondition(item.roofSideCondition.orEmpty())
            floorSideValue.setDrawableWithCondition(item.floorSideCondition.orEmpty())
        }
    }

    private fun AppCompatTextView.setDrawableWithCondition(condition: String) {
        val drawableId = if (condition == ConditionEnum.GOOD.type) {
            R.drawable.ic_check_18dp
        } else {
            R.drawable.ic_warning_18dp
        }
        this.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            drawableId,
            0
        )
    }

    companion object {
        fun create(
            parent: ViewGroup,
            isHistoryDetail: Boolean = false,
            onItemClicked: (HistoryModel) -> Unit
        ): HistoryViewHolder {
            val view = DataBindingUtil
                .inflate<ItemHistoryBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_history,
                    parent,
                    false
                )
            return HistoryViewHolder(view, isHistoryDetail, onItemClicked)
        }
    }
}