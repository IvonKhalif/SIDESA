package com.gov.sidesa.utils.picker

import com.gov.sidesa.R


sealed class SelectImageMenuType(
    val icon: Int, val title: Int
) {
    object Gallery : SelectImageMenuType(
        icon = R.drawable.ic_menu_gallery,
        title = R.string.gallery_label
    )

    object Camera : SelectImageMenuType(
        icon = R.drawable.ic_menu_camera,
        title = R.string.camera_label
    )
}