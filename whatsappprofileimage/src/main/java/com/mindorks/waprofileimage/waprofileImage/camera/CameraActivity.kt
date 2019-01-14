package com.mindorks.waprofileimage.waprofileImage.camera

import android.annotation.SuppressLint
import android.content.Intent
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraMetadata
import android.hardware.camera2.CaptureRequest
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.mindorks.waprofileimage.R
import com.mindorks.waprofileimage.waprofileImage.WAProfileImage
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File


class CameraActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        setTitle(null)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit()

        }else
        {
            // TODO Handle Other DEVICES

        }


    }

}
