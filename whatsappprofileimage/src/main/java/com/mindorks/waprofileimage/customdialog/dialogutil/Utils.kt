package com.mindorks.waprofileimage.customdialog.dialogutil

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.widget.AbsListView

import com.mindorks.waprofileimage.R
import com.mindorks.waprofileimage.customdialog.animation.HeightAnimation

 object Utils {

    private val INVALID = -1

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun animateContent(view: View, to: Int, listener: Animation.AnimationListener) {
        val animation = HeightAnimation(view, view.height, to)
        animation.setAnimationListener(listener)
        animation.duration = 200
        view.startAnimation(animation)
    }

    fun listIsAtTop(listView: AbsListView): Boolean {
        return listView.childCount == 0 || listView.getChildAt(0).top == listView.paddingTop
    }

    /**
     * This will be called in order to create view, if the given view is not null,
     * it will be used directly, otherwise it will check the resourceId
     *
     * @return null if both resourceId and view is not set
     */
    fun getView(context: Context, resourceId: Int, view: View?): View? {
        var view = view
        val inflater = LayoutInflater.from(context)
        if (view != null) {
            return view
        }
        if (resourceId != INVALID) {
            view = inflater.inflate(resourceId, null)
        }
        return view
    }

    /**
     * Get default animation resource when not defined by the user
     *
     * @param gravity       the gravity of the dialog
     * @param isInAnimation determine if is in or out animation. true when is is
     * @return the id of the animation resource
     */
    fun getAnimationResource(gravity: Int, isInAnimation: Boolean): Int {
        return if (gravity and Gravity.BOTTOM == Gravity.BOTTOM) {
            if (isInAnimation) R.anim.slide_in_bottom else R.anim.slide_out_bottom
        } else INVALID
    }
}// no instance
