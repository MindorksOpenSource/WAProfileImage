package com.mindorks.waprofileimage.waprofileImage

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import com.mindorks.waprofileimage.R

object WAProfileImage :  WAProfileImageView {
   @SuppressLint("StaticFieldLeak")
   private lateinit var presenter: WAProfileImagePresenter
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun launch(context: FragmentActivity, requestCode: Int) {
        presenter = WAProfileImagePresenter(this, context)
        presenter.launchOptionDialog(context, requestCode)

    }

}