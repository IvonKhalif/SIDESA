package com.gov.sidesa.ui.letterlist

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.data.letterlist.models.LettersModel
import com.gov.sidesa.databinding.ActivityLetterListBinding
import com.gov.sidesa.utils.constants.LetterConstant
import com.gov.sidesa.utils.enums.CategoryLetterEnum

class LetterListActivity : BaseActivity() {

    private lateinit var binding: ActivityLetterListBinding
    private val category by lazy {
        intent.getStringExtra(LetterConstant.EXTRA_LETTER_CATEGORY).orEmpty()
    }
    private lateinit var pagerAdapter: LetterListPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLetterListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainView()
    }

    private fun mainView() {
        initViewPager()
        setUpTabLayout()
        with(binding) {
            customToolbar.toolbarDetailProfile.apply {
                title =
                    if (category == CategoryLetterEnum.NEED_APPROVAL.category)
                        getString(R.string.letter_list_need_approval_title)
                    else
                        getString(R.string.letter_list_need_approval_title)
                setNavigationOnClickListener { finish() }
            }

        }
    }

    private fun initViewPager() {
        pagerAdapter =
            LetterListPagerAdapter(this, this, category == CategoryLetterEnum.SUBMISSION.category)
        binding.vpLetter.adapter = pagerAdapter
        binding.vpLetter.isSaveEnabled = false
    }

    private fun setUpTabLayout() {
        TabLayoutMediator(binding.tabLayoutLetter, binding.vpLetter) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.general_captio_all)
                1 -> getString(R.string.letter_list_waiting_approval_rt_caption)
                2 -> getString(R.string.letter_list_waiting_approval_rw_caption)
                else -> getString(R.string.letter_list_ready_for_pickup_caption)
            }
        }.attach()
        binding.tabLayoutLetter.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
//                tab?.let { pagerAdapter.setOnSelectView(binding.tabLayoutLetter, it.position) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                tab?.let { pagerAdapter.setUnSelectView(binding.tabLayoutLetter, it.position) }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })
    }
}