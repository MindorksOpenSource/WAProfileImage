package com.mindorks.waprofileimage.waprofileImage.newcam.implementations

import android.content.Context
import android.hardware.Camera
import com.mindorks.waprofileimage.waprofileImage.newcam.interfaces.MyCamera

@Suppress("DEPRECATION")
class MyCameraOneImpl(val context: Context) : MyCamera() {
    override fun getFrontCameraId() = Camera.CameraInfo.CAMERA_FACING_FRONT

    override fun getBackCameraId() = Camera.CameraInfo.CAMERA_FACING_BACK

    override fun getCountOfCameras() = Camera.getNumberOfCameras()
}
