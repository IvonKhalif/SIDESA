package com.gov.sidesa.ui.dashboard

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.domain.letter.list.models.LetterApprovalModel
import com.gov.sidesa.domain.letter.list.models.LetterSubmissionModel
import com.gov.sidesa.domain.letter.list.usecase.ApprovalLettersUseCase
import com.gov.sidesa.domain.letter.list.usecase.SubmissionLetterUseCase
import com.gov.sidesa.ui.letter.input.LetterInputActivity
import com.gov.sidesa.utils.PostLiveData
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.extension.orZero
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

/**
 * Created by yovi.putra on 29/07/22"
 * Project name: SIDESA
 **/


@FlowPreview
class DashboardViewModel(
    private val submissionLetterUseCase: SubmissionLetterUseCase,
    private val approvalLetterUseCase: ApprovalLettersUseCase
) : BaseViewModel() {

    private val _notificationState = MutableLiveData<Pair<String, String>>()
    val notificationState: LiveData<Pair<String, String>> get() = _notificationState

    val submissionLettersLiveData = PostLiveData<List<LetterSubmissionModel>?>()
    val approvalLettersLiveData = PostLiveData<LetterApprovalModel?>()

    init {
        getSubmissionLetters()
    }

    fun onLetterTemplateResult(resultCode: Int, intent: Intent?) = viewModelScope.launch {
        if (resultCode == Activity.RESULT_OK && intent != null) {
            val title = intent.getStringExtra(LetterInputActivity.RESULT_TITLE).orEmpty()
            val description = intent.getStringExtra(LetterInputActivity.RESULT_CONTENT).orEmpty()

            _notificationState.value = Pair(title, description)
        }
    }

    fun getSubmissionLetters() = viewModelScope.launch {
        val user = PreferenceUtils.getAccountUserResponse()
        showLoadingWidget()

        when (val result =
            submissionLetterUseCase.invoke(accountId = user?.id.orZero())) {
            is NetworkResponse.Success -> {
                submissionLettersLiveData.post(result.body).also {
                    getApprovalLetters()
                }
            }
            else -> onResponseNotSuccess(response = result)
        }
    }

    private fun getApprovalLetters() = viewModelScope.launch {
        val user = PreferenceUtils.getAccount()

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