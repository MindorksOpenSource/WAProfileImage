package com.mindorks.waprofileimage.customdialog.callback

import com.mindorks.waprofileimage.customdialog.DialogCustomization

/**
 * dialogcustomization will use this listener to propagate cancel events when back button is pressed.
 */
interface OnCancelListener {

    fun onCancel(dialog: DialogCustomization)
}
