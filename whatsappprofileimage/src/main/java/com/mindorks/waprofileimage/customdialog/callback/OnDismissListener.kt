package com.mindorks.waprofileimage.customdialog.callback

import com.mindorks.waprofileimage.customdialog.DialogCustomization

/**
 * Invoked when dialog is completely dismissed. This listener takes the animation into account and waits for it.
 *
 *
 * It is invoked after animation is completed
 */
interface OnDismissListener {
    fun onDismiss(dialog: DialogCustomization)
}
