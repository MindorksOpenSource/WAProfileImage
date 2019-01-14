package com.simplemobiletools.commons.views

import android.content.Context
import android.content.res.ColorStateList
import android.support.v7.widget.AppCompatCheckBox
import android.util.AttributeSet
import com.simplemobiletools.commons.R

class MyAppCompatCheckbox : AppCompatCheckBox {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    fun setColors(textColor: Int, accentColor: Int, backgroundColor: Int) {
        setTextColor(textColor)
        val colorStateList = ColorStateList(
                arrayOf(intArrayOf(-android.R.attr.state_checked),
                        intArrayOf(android.R.attr.state_checked)
                ),
                intArrayOf(context.resources.getColor(R.color.radiobutton_disabled), accentColor)
        )
        supportButtonTintList = colorStateList
    }
}
