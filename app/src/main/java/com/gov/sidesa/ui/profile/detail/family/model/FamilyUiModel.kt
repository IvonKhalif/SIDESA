package com.gov.sidesa.ui.profile.detail.family.model

import android.content.Context
import com.gov.sidesa.R
import com.gov.sidesa.domain.regions.models.Region
import com.gov.sidesa.ui.profile.edit.family.models.RelationType
import com.gov.sidesa.utils.extension.format
import java.util.*

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

data class FamilyUiModel(
    val relationType: RelationType = RelationType.Father,
    val name: String = "",
    val ktpNumber: String = "",
    val birthPlace: String = "",
    val birthDate: Date = Date(),
    val address: String = "",
    val rt: String = "",
    val rw: String = "",
    val province: Region = Region(),
    val city: Region = Region(),
    val district: Region = Region(),
    val village: Region = Region()
) {

    val fullAddress
        get() = "$address, RT$rt RW$rw, ${province.name}, " +
                "${city.name}, ${district.name}, ${village.name}"
                    .trim { it == ' ' || it == ',' }

    fun getRelation(context: Context) = when (relationType) {
        is RelationType.Father -> context.getString(R.string.family_data_father)
        is RelationType.Mother -> context.getString(R.string.family_data_mother)
        is RelationType.Husband -> context.getString(R.string.family_data_husband)
        is RelationType.Wife -> context.getString(R.string.family_data_wife)
        is RelationType.Child -> context.getString(
            R.string.family_data_child_to,
            relationType.count.toString()
        )
    }

    val birthPlaceAndDate
        get() = "$birthPlace, ${birthDate.format()}"
}
