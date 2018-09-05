package com.mindorks.waprofileimage.waprofileImage

import android.content.Intent
import android.os.Build
import android.support.v4.app.FragmentActivity
import com.mindorks.waprofileimage.callback.TaskFinished
import com.mindorks.waprofileimage.util.PermUtil
import com.mindorks.waprofileimage.waprofileImage.wapprofileimageactivity.WAProfileImageActivity


object WAProfileImage {
    fun launch(context: FragmentActivity, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermUtil.checkForCamara_WritePermissions(context, object : TaskFinished {
                override fun onTaskFinished(check: Boolean?) {
                    val i = Intent(context, WAProfileImageActivity::class.java)
                    i.putExtra(WAProfileImageActivity.REQUEST_CODE_KEY, requestCode);
                    context.startActivityForResult(i, requestCode)
                }
            })
        } else {
            val i = Intent(context, WAProfileImageActivity::class.java)
            i.putExtra(WAProfileImageActivity.REQUEST_CODE_KEY, requestCode);
            context.startActivityForResult(i, requestCode)


        }


    }

}