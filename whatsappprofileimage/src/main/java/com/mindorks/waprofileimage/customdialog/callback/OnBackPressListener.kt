package com.mindorks.waprofileimage.customdialog.callback

import com.mindorks.waprofileimage.customdialog.DialogCustomization

/**
 * dialogcustomization tries to listen back press actions.
 */
interface OnBackPressListener {

    /**
     * Invoked when dialogcustomization receives any back press button event.
     */
    fun onBackPressed(dialog: DialogCustomization)

}
