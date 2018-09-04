package com.mindorks.waprofileimage

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View

class WAProfileImage : AppCompatActivity(), WAProfileImageView {
    lateinit var presenter: WAProfileImagePresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = WAProfileImagePresenter(this, this)
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun launch(context: FragmentActivity, requestCode: Int) {
        presenter.launchOptionDialog(context, requestCode)

    }

}