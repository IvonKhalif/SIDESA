package com.example.containertracker.ui.home.containercondition

import androidx.lifecycle.MutableLiveData
import com.example.containertracker.base.BaseViewModel
import com.example.containertracker.utils.enums.ConditionEnum
import com.example.containertracker.utils.enums.StatusContainerEnum

class ContainerConditionViewModel : BaseViewModel() {
    val sealNumber = MutableLiveData<String>()
    val salesOrderNumber = MutableLiveData<String>()
    var upperCondition = MutableLiveData<ConditionEnum>()
    var floorCondition = MutableLiveData<ConditionEnum>()
    var doorCondition = MutableLiveData<ConditionEnum>()
    var backCondition = MutableLiveData<ConditionEnum>()
    var leftCondition = MutableLiveData<ConditionEnum>()
    var rightCondition = MutableLiveData<ConditionEnum>()
}