package com.example.containertracker.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.containertracker.base.BaseViewModel
import com.example.containertracker.data.container.models.Container
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class ReportViewModel : BaseViewModel() {

    var startDate = MutableLiveData(LocalDate.now())
    var startDateString: LiveData<String> = Transformations.map(startDate) {
        when (it) {
            null -> ""
            LocalDate.now() -> "Today"
            else -> it.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
        }
    }

    var endDate = MutableLiveData(LocalDate.now())
    var endDateString: LiveData<String> = Transformations.map(endDate) {
        when (it) {
            null -> ""
            LocalDate.now() -> "Today"
            else -> it.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
        }
    }

    var keywordSearch = MutableLiveData("")
    var containerData = MutableLiveData<Container>()
}