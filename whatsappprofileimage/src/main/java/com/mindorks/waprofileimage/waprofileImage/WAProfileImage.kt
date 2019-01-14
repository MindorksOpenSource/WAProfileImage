package com.mindorks.waprofileimage.waprofileImage

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.FragmentActivity
import com.mindorks.waprofileimage.callback.TaskFinished
import com.mindorks.waprofileimage.util.PermUtil
import com.mindorks.waprofileimage.waprofileImage.wapprofileimageactivity.WAProfileImageActivity


object WAProfileImage {

    val REQUEST_CODE_KEY = "REQUEST_CODE"
    val RESPONSE_CODE_REMOVE_IMAGE = 3
    val RESPONSE_CODE_OPEN_CAMERA = 2
    val RESPONSE_CODE_OPEN_GALLERY = 1
    val REQUEST_CODE_DEFAULT_VALUE = 0
    val IMAGE_PICKED_KEY = "IMAGE_KEY"

    fun launch(context: FragmentActivity, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermUtil.checkForCamara_WritePermissions(context, object : TaskFinished {
                override fun onTaskFinished(check: Boolean?) {
                    val i = Intent(context, WAProfileImageActivity::class.java)
                    i.putExtra(REQUEST_CODE_KEY, requestCode);
                    context.startActivityForResult(i, requestCode)
                }
            })
        } else {
            val i = Intent(context, WAProfileImageActivity::class.java)
            i.putExtra(REQUEST_CODE_KEY, requestCode);
            context.startActivityForResult(i, requestCode)


        }


    }

    fun getCameraBitMapImage(data: Intent): Bitmap {
        val byteArray = data.getByteArrayExtra(IMAGE_PICKED_KEY)
        val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        return bmp
    }

}