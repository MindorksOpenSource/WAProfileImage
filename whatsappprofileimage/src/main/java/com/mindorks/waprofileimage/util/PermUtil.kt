package com.mindorks.waprofileimage.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentActivity
import com.mindorks.waprofileimage.callback.TaskFinished
import java.util.ArrayList

object PermUtil {
    val REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 9921

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun checkForCamara_WritePermissions(activity: FragmentActivity, taskFinished: TaskFinished) {
        val permissionsNeeded = ArrayList<String>()
        val permissionsList = ArrayList<String>()
        if (!addPermission(permissionsList, Manifest.permission.CAMERA, activity))
            permissionsNeeded.add("CAMERA")
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE, activity))
            permissionsNeeded.add("WRITE_EXTERNAL_STORAGE")
        if (permissionsList.size > 0) {
            activity.requestPermissions(permissionsList.toTypedArray(),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS)
        } else {
            taskFinished.onTaskFinished(true)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun addPermission(permissionsList: MutableList<String>, permission: String, ac: Activity): Boolean {
        if (ac.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission)
            // Check for Rationale Option
            return ac.shouldShowRequestPermissionRationale(permission)
        }
        return true
    }

}