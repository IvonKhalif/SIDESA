package com.gov.sidesa.ui.profile.edit.family.models

import android.content.Context
import com.gov.sidesa.R

/**
 * Created by yovi.putra on 31/07/22"
 * Project name: SIDESA
 **/

sealed class RelationType(val type: String) {
    object Mother : RelationType("ibu")
    object Father : RelationType("ayah")
    object Wife : RelationType("istri")
    object Husband : RelationType("suami")
    data class Child(val count: Int) : RelationType("anak")

    companion object {
        fun find(value: String, childCount: Int = 1): RelationType {
            return when (value.lowercase()) {
                Mother.type -> Mother
                Father.type -> Father
                Wife.type -> Wife
                Husband.type -> Husband
                else -> Child(childCount)
            }
        }
    }

    fun getRelation(context: Context) = when (this) {
        is Father -> context.getString(R.string.family_data_father)
        is Mother -> context.getString(R.string.family_data_mother)
        is Husband -> context.getString(R.string.family_data_husband)
        is Wife -> context.getString(R.string.family_data_wife)
        is Child -> context.getString(
            R.string.family_data_child_to,
            this.count.toString()
        )
    }
}
