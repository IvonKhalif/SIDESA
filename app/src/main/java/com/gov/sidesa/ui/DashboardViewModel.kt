package com.gov.sidesa.ui

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.ui.letter.input.LetterInputActivity
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

/**
 * Created by yovi.putra on 29/07/22"
 * Project name: SIDESA
 **/


@FlowPreview
class DashboardViewModel : BaseViewModel() {

    private val _notificationState = MutableLiveData<Pair<String, String>>()
    val notificationState: LiveData<Pair<String, String>> get() = _notificationState

    fun onLetterTemplateResult(resultCode: Int, intent: Intent?) = viewModelScope.launch {
        if (resultCode == Activity.RESULT_OK && intent != null) {
            val title = intent.getStringExtra(LetterInputActivity.RESULT_TITLE).orEmpty()
            val description = intent.getStringExtra(LetterInputActivity.RESULT_CONTENT).orEmpty()

            _notificationState.value = Pair(title, description)
        }
    }
}