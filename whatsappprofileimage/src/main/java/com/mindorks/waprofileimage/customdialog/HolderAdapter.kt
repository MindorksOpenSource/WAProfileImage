package com.mindorks.waprofileimage.customdialog

import android.widget.BaseAdapter

interface HolderAdapter : Holder {

    fun setAdapter(adapter: BaseAdapter)

    fun setOnItemClickListener(listener: OnHolderListener)
}
