package com.mindorks.waprofileimage.waprofileImage.wapprofileimageactivity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.mindorks.waprofileimage.R
import com.mindorks.waprofileimage.waprofileImage.camera.CameraActivity

class WAProfileImageActivity : AppCompatActivity(), WAProfileImageView {

    override fun writeMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        val REQUEST_CODE_KEY = "requestCode"
        val RESPONSE_CODE_REMOVE_IMAGE = 3
        val RESPONSE_CODE_OPEN_CAMERA = 2
        val RESPONSE_CODE_OPEN_GALLERY = 1
    }

    val REQUEST_CODE_DEFAULT_VALUE = 0
    private lateinit var presenter: WAPProfileImageActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waprofile_image)
        setTitle(null)
        presenter = WAPProfileImageActivityPresenter(this, this)
        presenter.launchOptionDialog(this, intent.getIntExtra(REQUEST_CODE_KEY, REQUEST_CODE_DEFAULT_VALUE))


    }

    override fun openCamera() {
        val i = Intent(this, CameraActivity::class.java)
        startActivityForResult(i,intent.getIntExtra(REQUEST_CODE_KEY, REQUEST_CODE_DEFAULT_VALUE))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // this for from camera activity it will be handled
        if (data != null) {
            setNewResult(resultCode,data)
        }else{
            setNewResult(resultCode)
        }
        finish()
    }

    override fun setNewResult(resultCode: Int) {
        setResult(resultCode)
    }

    override fun setNewResult(resultCode: Int, data: Intent) {
        setResult(resultCode, data)

    }

    override fun onViewFinished() {
        // finish()
    }

    override fun launch(context: FragmentActivity, requestCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}