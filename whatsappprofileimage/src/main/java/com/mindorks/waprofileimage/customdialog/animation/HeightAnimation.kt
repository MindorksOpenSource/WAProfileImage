package com.mindorks.waprofileimage.customdialog.animation

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation

internal class HeightAnimation(private val view: View, private val originalHeight: Int, toHeight: Int) : Animation() {
    private val perValue: Float

    init {
        this.perValue = (toHeight - originalHeight).toFloat()
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        view.layoutParams.height = (originalHeight + perValue * interpolatedTime).toInt()
        view.requestLayout()
    }

    override fun willChangeBounds(): Boolean {
        return true
    }
}