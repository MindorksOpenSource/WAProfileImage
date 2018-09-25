package com.mindorks.waprofileimage.waprofileImage.newcam.interfaces

abstract class MyCamera {
    abstract fun getFrontCameraId(): Int

    abstract fun getBackCameraId(): Int

    abstract fun getCountOfCameras(): Int
}
