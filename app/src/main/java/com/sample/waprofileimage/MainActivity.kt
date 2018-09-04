package com.sample.waprofileimage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mindorks.waprofileimage.waprofileImage.WAProfileImage
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.sample_image).setOnClickListener { v ->
            WAProfileImage.launch(this,105)

        }


    }
}
