package com.gov.sidesa.ui.regions

/**
 * Created by yovi.putra on 31/07/22"
 * Project name: SIDESA
 **/

sealed class RegionType(val type: Int) {
    object Province : RegionType(type = 1)
    object City : RegionType(type = 2)
    object District : RegionType(type = 3)
    object Village : RegionType(type = 4)
}
