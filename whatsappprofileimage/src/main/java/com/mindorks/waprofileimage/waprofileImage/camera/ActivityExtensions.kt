package com.mindorks.waprofileimage.waprofileImage.camera

import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.widget.Toast

/**
 * This file illustrates Kotlin's Extension Functions by extending FragmentActivity.
 */

/**
 * Shows a [Toast] on the UI thread.
 *
 * @param text The message to show
 */
fun FragmentActivity.showToast(text: String) {
    runOnUiThread { Toast.makeText(this, text, Toast.LENGTH_SHORT).show() }
}
