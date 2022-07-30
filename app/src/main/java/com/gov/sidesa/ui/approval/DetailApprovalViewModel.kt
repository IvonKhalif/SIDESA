package com.gov.sidesa.ui.approval

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.data.letterdetail.models.DetailApprovalResponse
import com.gov.sidesa.domain.letter.detail.GetLetterDetailUseCase
import com.gov.sidesa.domain.letter.detail.models.DetailApprovalModel
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

class DetailApprovalViewModel(
    private val getDetail: GetLetterDetailUseCase
) : BaseViewModel() {
    private val _letterDetail = MutableLiveData<DetailApprovalModel>()
    val letterDetail: LiveData<DetailApprovalModel> get() = _letterDetail

    fun onLoad(letterTypeId: String, letterName: String) = viewModelScope.launch {
        showLoadingWidget()

        when (val response =
            getDetail.invoke(letterTypeId = letterTypeId, letterName = letterName)) {
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
}