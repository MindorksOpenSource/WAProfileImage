package com.mindorks.waprofileimage.waprofileImage

import android.support.v4.app.FragmentActivity
import android.view.MotionEvent
import android.view.View

interface WAProfileImageView :View.OnTouchListener{
    fun launch(context: FragmentActivity, requestCode: Int)
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean
}