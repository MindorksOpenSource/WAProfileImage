package com.sample.waprofileimage

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.mindorks.waprofileimage.waprofileImage.WAProfileImage
import com.mindorks.waprofileimage.waprofileImage.WAProfileImage.IMAGE_PICKED_KEY
import com.mindorks.waprofileimage.waprofileImage.wapprofileimageactivity.WAProfileImageActivity
import android.graphics.BitmapFactory
import android.graphics.Bitmap



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.sample_image).setOnClickListener { v ->
            WAProfileImage.launch(this, 105)


        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    if(resultCode==WAProfileImage.RESPONSE_CODE_OPEN_CAMERA)
    {
        if (data != null) {
            // get image Path
           val imagePath=data.getStringExtra(IMAGE_PICKED_KEY)

        }
    }


    }
}
