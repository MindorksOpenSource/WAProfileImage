package com.mindorks.waprofileimage.customdialog

import android.support.annotation.ColorRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface Holder {

    val inflatedView: View

    val header: View?

    val footer: View?

    /**
     * Adds the given view as header to the top of holder
     */
    fun addHeader(view: View)

    /**
     * Adds the given view as header to the top of holder
     *
     * @param view  will be shown as header.
     * @param fixed fixed on top if it is true, scrollable otherwise
     */
    fun addHeader(view: View, fixed: Boolean)

    /**
     * Adds the given view as footer to the bottom of holder
     */
    fun addFooter(view: View)

    /**
     * Adds the given view as footer to the bottom of holder
     *
     * @param view  will be shown as footer.
     * @param fixed fixed at bottom if it is true, scrollable otherwise
     */
    fun addFooter(view: View, fixed: Boolean)

    /**
     * Sets the given color resource as background for the content
     */
    fun setBackgroundResource(@ColorRes colorResource: Int)

    fun getView(inflater: LayoutInflater, parent: ViewGroup): View

    fun setOnKeyListener(keyListener: View.OnKeyListener)

}
