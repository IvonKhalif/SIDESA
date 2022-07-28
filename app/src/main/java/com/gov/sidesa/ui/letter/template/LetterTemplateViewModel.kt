package com.gov.sidesa.ui.letter.template

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.domain.letter.template.usecases.GetTemplateUseCase
import com.gov.sidesa.ui.letter.template.models.LetterTemplateUiModel
import com.gov.sidesa.ui.letter.template.models.asUiModel
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

/**
 * Created by yovi.putra on 28/07/22"
 * Project name: SIDESA
 **/


class LetterTemplateViewModel(
    private val getTemplateUseCase: GetTemplateUseCase
): BaseViewModel() {
    
    private val _templateData = MutableLiveData<List<LetterTemplateUiModel>>()
    val templateData: LiveData<List<LetterTemplateUiModel>> get() = _templateData

    init {
        viewModelScope.launch {
            showLoadingWidget()

            when (val result = getTemplateUseCase.invoke()) {
                is NetworkResponse.Success -> {
                    _templateData.value = result.body.asUiModel()
                }
                else -> onResponseNotSuccess(response = result)
            }

            hideLoadingWidget()
        }
    }
}