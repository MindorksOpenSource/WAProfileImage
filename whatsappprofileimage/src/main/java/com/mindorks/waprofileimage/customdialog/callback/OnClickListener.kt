package com.mindorks.waprofileimage.customdialog.callback

import android.view.View

import com.mindorks.waprofileimage.customdialog.DialogCustomization

interface OnClickListener {

    /**
     * Invoked when any view within ViewHolder is clicked.
     *
     * @param view is the clicked view
     */
    fun onClick(dialog: DialogCustomization, view: View)

}
