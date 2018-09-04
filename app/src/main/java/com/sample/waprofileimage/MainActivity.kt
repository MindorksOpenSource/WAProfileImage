package com.sample.waprofileimage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mindorks.waprofileimage.waprofileImage.WAProfileImage

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WAProfileImage.launch(this,105)

    }
}
