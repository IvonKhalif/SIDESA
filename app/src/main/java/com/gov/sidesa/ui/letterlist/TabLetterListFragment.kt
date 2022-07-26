package com.gov.sidesa.ui.letterlist

import androidx.core.os.bundleOf
import com.gov.sidesa.base.BaseFragment
import com.gov.sidesa.utils.constants.LetterConstant.ARG_IS_SUBMISSION_PAGE
import com.gov.sidesa.utils.constants.LetterConstant.ARG_TAB_CATEGORY

class TabLetterListFragment: BaseFragment() {
    companion object {
        internal fun newInstance(type: String?, fromSubmissionPage: Boolean): TabLetterListFragment {
            val args = bundleOf(
                ARG_TAB_CATEGORY to type, ARG_IS_SUBMISSION_PAGE
                        to fromSubmissionPage
            )
            return TabLetterListFragment().apply {
                arguments = args
            }
        }
    }
}