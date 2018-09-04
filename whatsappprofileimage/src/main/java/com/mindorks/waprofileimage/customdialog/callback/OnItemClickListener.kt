package com.mindorks.waprofileimage.customdialog.callback

import android.view.View

import com.mindorks.waprofileimage.customdialog.DialogCustomization

interface OnItemClickListener {

    fun onItemClick(dialog: DialogCustomization, item: Any, view: View, position: Int)

}
