package com.example.containertracker.data.container.models

import android.os.Parcelable
import com.example.containertracker.utils.enums.ConditionEnum
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContainerConditionModel(
    var sealNumber: String?,
    var salesOrderNumber: String?,
    var upperCondition: ConditionEnum?,
    var floorCondition: ConditionEnum?,
    var doorCondition: ConditionEnum?,
    var backCondition: ConditionEnum?,
    var leftCondition: ConditionEnum?,
    var rightCondition: ConditionEnum?
): Parcelable