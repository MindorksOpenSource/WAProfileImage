package com.simplemobiletools.commons.views

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet

class MyLinearLayoutManager : LinearLayoutManager {
    constructor(context: Context) : super(context)


    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)

    // fixes crash java.lang.IndexOutOfBoundsException: Inconsistency detected...
    // taken from https://stackoverflow.com/a/33985508/1967672
    override fun supportsPredictiveItemAnimations() = false
}
