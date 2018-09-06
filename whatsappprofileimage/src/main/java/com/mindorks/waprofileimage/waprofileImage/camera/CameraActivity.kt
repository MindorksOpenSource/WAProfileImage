package com.mindorks.waprofileimage.waprofileImage.camera

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mindorks.waprofileimage.R

class CameraActivity : AppCompatActivity() {

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
