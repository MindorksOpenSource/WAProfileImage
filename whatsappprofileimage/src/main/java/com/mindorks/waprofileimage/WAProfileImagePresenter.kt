package com.mindorks.waprofileimage

import android.content.Context
import android.support.v4.app.FragmentActivity

class WAProfileImagePresenter constructor(wAProfileImageView :WAProfileImageView ,ctx:Context):WAProfileImagePresnterInterface  {
    override fun launchOptionDialog(context: FragmentActivity, requestCode: Int) {


    }

    var view : WAProfileImageView? = null
   var context:Context
    init {
        view=wAProfileImageView
        context=ctx

    }
}