package com.mindorks.waprofileimage.waprofileImage.camera

import android.content.Context
import android.content.pm.PackageManager

object Common {
     fun checkCameraHardware(context: Context): Boolean {
        if (context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true
        } else {
            // no camera on this device
            return false
        }
    }
}