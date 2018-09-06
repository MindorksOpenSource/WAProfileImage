package com.mindorks.waprofileimage.waprofileImage.wapprofileimageactivity

import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.view.MotionEvent
import android.view.View

interface WAProfileImageView :View.OnTouchListener{
    fun launch(context: FragmentActivity, requestCode: Int)
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean
    fun onViewFinished()
    fun setNewResult(resultCode:Int)
    fun setNewResult(resultCode:Int,data:Intent)
    fun writeMessage(message:String)
    fun openCamera()

}