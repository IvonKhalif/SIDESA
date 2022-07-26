package com.gov.sidesa.ui.letterlist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.gov.sidesa.R
import com.gov.sidesa.utils.enums.LetterSubmissionTypeEnum

class LetterListPagerAdapter(
    fa: FragmentActivity,
    private val context: Context,
    private val isSubmissionPage: Boolean
) : FragmentStateAdapter(fa) {

    private val fragmentList = listOf(
        TabLetterListFragment.newInstance(LetterSubmissionTypeEnum.ALL.type, isSubmissionPage),
        TabLetterListFragment.newInstance(LetterSubmissionTypeEnum.WAITING_APPROVAL_RT.type, isSubmissionPage),
        TabLetterListFragment.newInstance(LetterSubmissionTypeEnum.WAITING_APPROVAL_RW.type, isSubmissionPage),
        TabLetterListFragment.newInstance(LetterSubmissionTypeEnum.READY.type, isSubmissionPage)
    )

    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int) = fragmentList[position]

    fun setOnSelectView(tabLayout: TabLayout, position: Int) {
        val tab = tabLayout.getTabAt(position)
        val selected = tab!!.customView
        val tabTitle = selected!!.findViewById<TextView>(R.id.text)
        tabTitle.setTextColor(ContextCompat.getColor(context, R.color.black_484848))
    }

    fun setUnSelectView(tabLayout: TabLayout, position: Int) {
        val tab = tabLayout.getTabAt(position)
        val selected = tab!!.customView
        val tabTitle = selected!!.findViewById<TextView>(R.id.text)
        tabTitle.setTextColor(ContextCompat.getColor(context, R.color.gray_ADABAB))
    }
}