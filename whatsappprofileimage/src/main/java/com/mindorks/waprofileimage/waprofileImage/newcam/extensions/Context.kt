package com.mindorks.waprofileimage.waprofileImage.newcam.extensions

import android.content.Context
import android.graphics.Point
import android.view.WindowManager
import com.mindorks.waprofileimage.waprofileImage.newcam.helpers.Config
import com.mindorks.waprofileimage.waprofileImage.newcam.implementations.MyCameraOneImpl
import com.mindorks.waprofileimage.waprofileImage.newcam.implementations.MyCameraTwoImpl
import com.simplemobiletools.commons.helpers.isLollipopPlus
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

val Context.config: Config get() = Config.newInstance(applicationContext)

internal val Context.windowManager: WindowManager get() = getSystemService(Context.WINDOW_SERVICE) as WindowManager

fun Context.getOutputMediaFile(isPhoto: Boolean): String {
    val mediaStorageDir = File(config.savePhotosFolder)

    if (!mediaStorageDir.exists()) {
        if (!mediaStorageDir.mkdirs()) {
            return ""
        }
    }

    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    return if (isPhoto) {
        "${mediaStorageDir.path}/IMG_$timestamp.jpg"
    } else {
        "${mediaStorageDir.path}/VID_$timestamp.mp4"
    }
}

val Context.usableScreenSize: Point
    get() {
        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        return size
    }

val Context.realScreenSize: Point
    get() {
        val size = Point()
        windowManager.defaultDisplay.getRealSize(size)
        return size
    }

val Context.navBarHeight: Int get() = realScreenSize.y - usableScreenSize.y

fun Context.getMyCamera() = if (isLollipopPlus()) MyCameraTwoImpl(applicationContext) else MyCameraOneImpl(applicationContext)
