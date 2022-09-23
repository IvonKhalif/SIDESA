package com.gov.sidesa.ui.letter.list

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.gov.sidesa.R
import com.gov.sidesa.ui.letter.list.tab.TabLetterListFragment
import com.gov.sidesa.utils.enums.TypeApprovalEnum
import com.gov.sidesa.utils.enums.TypeSubmissionEnum

class LetterListPagerAdapter(
    fa: FragmentActivity,
    private val context: Context,
    private val isSubmissionPage: Boolean
) : FragmentStateAdapter(fa) {

    private val fragmentList = getListFragment()

    private fun getListFragment() = if (isSubmissionPage)
        listOf(
            TabLetterListFragment.newInstance("", isSubmissionPage),
            TabLetterListFragment.newInstance(
                TypeSubmissionEnum.RT_WAITING_Submission.type,
                isSubmissionPage
            ),
            TabLetterListFragment.newInstance(
                TypeSubmissionEnum.RW_WAITING_Submission.type,
                isSubmissionPage
            ),
            TabLetterListFragment.newInstance(
                TypeSubmissionEnum.VILLAGE_WAITING_Submission.type,
                isSubmissionPage
            ),
            TabLetterListFragment.newInstance(TypeSubmissionEnum.FINISH.type, isSubmissionPage)
        )
    else
        listOf(
            TabLetterListFragment.newInstance("", isSubmissionPage),
            TabLetterListFragment.newInstance(
                TypeApprovalEnum.NOT_APPROVED_YET.type,
                isSubmissionPage
            ),
            TabLetterListFragment.newInstance(
                TypeApprovalEnum.APPROVED.type,
                isSubmissionPage
            ),
            TabLetterListFragment.newInstance(TypeApprovalEnum.REJECTED.type, isSubmissionPage)
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