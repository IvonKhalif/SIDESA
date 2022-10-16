package com.gov.sidesa.ui.approval

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.data.letterdetail.request.DoApprovalRequest
import com.gov.sidesa.domain.letter.detail.DoApprovalUseCase
import com.gov.sidesa.domain.letter.detail.GetLetterDetailUseCase
import com.gov.sidesa.domain.letter.detail.models.DetailApprovalModel
import com.gov.sidesa.utils.PostLiveData
import com.gov.sidesa.utils.enums.StatusResponseEnum
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

class DetailApprovalViewModel(
    private val getDetail: GetLetterDetailUseCase,
    private val doApproval: DoApprovalUseCase
) : BaseViewModel() {
    private val _letterDetail = MutableLiveData<DetailApprovalModel>()
    val letterDetail: LiveData<DetailApprovalModel> get() = _letterDetail
    val statusApprovalPassword = PostLiveData<String?>()

    fun onLoad(letterId: String) = viewModelScope.launch {
        showLoadingWidget()

        when (val response =
            getDetail.invoke(letterId = letterId)) {
            is NetworkResponse.Success -> {
                _letterDetail.value = response.body
            }
            else -> {
                onResponseNotSuccess(response = response)
//                _closeViewState.value = Unit
            }
        }

        hideLoadingWidget()
    }

    fun doApproval(request: DoApprovalRequest) = viewModelScope.launch {
        showLoadingWidget()

        when (val result = doApproval.invoke(request)) {
            is NetworkResponse.Success -> {
                result.body.status.let {
                    if (it == StatusResponseEnum.SUCCESS.status) {
                        statusApprovalPassword.post(result.body.desc)
                    }
                }
            }
            else -> onResponseNotSuccess(response = result)
        }


        hideLoadingWidget()
    }
}