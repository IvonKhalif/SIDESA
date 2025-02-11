package com.gov.sidesa.base

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun showImmediately(
    fragmentManager: FragmentManager,
    tag: String,
    fragmentFactory: () -> DialogFragment
) {
    // find dialog to fragment manager, if not found so invoke to fragment factory
    val dialog = (fragmentManager.findFragmentByTag(tag) as? DialogFragment)
        ?: fragmentFactory.invoke()

    // has already been saved by its host
    if (!dialog.isStateSaved) {
        try {
            dialog.showNow(fragmentManager, tag)
        } catch (e: Throwable) {

        }
    }
}

fun FragmentManager.findSheetByTag(tag: String): BottomSheetDialogFragment? {
    return (findFragmentByTag(tag) as? BottomSheetDialogFragment)
}