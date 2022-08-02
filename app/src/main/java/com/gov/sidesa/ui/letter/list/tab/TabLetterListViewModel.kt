package com.gov.sidesa.ui.letter.list.tab

import androidx.lifecycle.viewModelScope
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.domain.letter.list.models.LetterApprovalModel
import com.gov.sidesa.domain.letter.list.models.LetterSubmissionModel
import com.gov.sidesa.domain.letter.list.usecase.ApprovalLettersUseCase
import com.gov.sidesa.domain.letter.list.usecase.SubmissionLetterUseCase
import com.gov.sidesa.utils.PostLiveData
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.extension.orZero
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

class TabLetterListViewModel(
    private val submissionLetterUseCase: SubmissionLetterUseCase,
    private val approvalLetterUseCase: ApprovalLettersUseCase
) : BaseViewModel() {
    val submissionLettersLiveData = PostLiveData<List<LetterSubmissionModel>?>()
    val approvalLettersLiveData = PostLiveData<LetterApprovalModel?>()

    fun getSubmissionLetters() = viewModelScope.launch {
        val user = PreferenceUtils.getUser()
        showLoadingWidget()

        when (val result =
            submissionLetterUseCase.invoke(accountId = user?.id.orZero())) {
            is NetworkResponse.Success -> {
                submissionLettersLiveData.post(result.body)
            }
            else -> onResponseNotSuccess(response = result)
        }
        hideLoadingWidget()
    }

    fun getApprovalLetters() = viewModelScope.launch {
        val user = PreferenceUtils.getUser()
        showLoadingWidget()
        when (val result =
            approvalLetterUseCase.invoke(accountId = user?.id.orZero())) {
            is NetworkResponse.Success -> {
                approvalLettersLiveData.post(result.body)
            }
            else -> onResponseNotSuccess(response = result)
        }
        hideLoadingWidget()
    }

}