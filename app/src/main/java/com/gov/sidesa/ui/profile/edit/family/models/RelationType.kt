package com.gov.sidesa.ui.profile.edit.family.models

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
        fun find(value: String): RelationType {
            return when (value.lowercase()) {
                Mother.type -> Mother
                Father.type -> Father
                Wife.type -> Wife
                Husband.type -> Husband
                else -> Child(1)
            }
        }
    }
}
