package com.gov.sidesa.ui.registration.ktp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistrationKTPViewModel : ViewModel() {

    var increaseProgress = MutableLiveData(false)
    var decreaseProgress = MutableLiveData(false)

}