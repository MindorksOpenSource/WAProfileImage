package com.mindorks.waprofileimage.waprofileImage.wapprofileimageactivity

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.mindorks.waprofileimage.R
import com.mindorks.waprofileimage.customdialog.DialogCustomization
import com.mindorks.waprofileimage.customdialog.ListHolder
import com.mindorks.waprofileimage.customdialog.SimpleAdapter
import com.mindorks.waprofileimage.customdialog.callback.*

class WAPProfileImageActivityPresenter constructor(wAProfileImageView: WAProfileImageView, ctx: Context)
    : WAProfileImagePresnterInterface, OnClickListener,
        OnDismissListener,
        OnItemClickListener, OnCancelListener, OnBackPressListener {
    var requestCode: Int? = null
    var context: Context

    var view: WAProfileImageView? = null

    init {
        view = wAProfileImageView
        context = ctx

    }

    override fun launchOptionDialog(context: FragmentActivity, requestCode: Int) {
        showCustomeDialog(context, requestCode)

    }

    override fun onClick(dialog: DialogCustomization, view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDismiss(dialog: DialogCustomization) {

        view!!.onViewFinished()
    }

    override fun onItemClick(dialog: DialogCustomization, item: Any, view: View, position: Int) {
        handleEachItemClicked(position)
    }

    override fun onCancel(dialog: DialogCustomization) {
        view!!.onViewFinished()
    }

    override fun onBackPressed(dialog: DialogCustomization) {
        view!!.onViewFinished()
    }

    private fun showCustomeDialog(context: FragmentActivity, requestCode: Int) {
        this.requestCode = requestCode
        val holder = ListHolder()
        val adapter = SimpleAdapter(context, 3)
        val builder = DialogCustomization.newDialog(context).apply {
            setContentHolder(holder)
            setHeader(R.layout.header, true)
            setFooter(R.layout.footer, true)
            setCancelable(true)
            setGravity(Gravity.BOTTOM)
            setAdapter(adapter)
            setOnClickListener(this@WAPProfileImageActivityPresenter)
            setOnItemClickListener(this@WAPProfileImageActivityPresenter)
            setOnCancelListener(this@WAPProfileImageActivityPresenter)
            setOnDismissListener(this@WAPProfileImageActivityPresenter)
            setOnBackPressListener(this@WAPProfileImageActivityPresenter)
            setExpanded(false, 450)
            setOverlayBackgroundResource(android.R.color.transparent)

        }

        builder.create().show()


    }

    private fun handleEachItemClicked(position: Int) {
        when (position) {
            -1 -> {
                // TODO open Gallary and get the image path and set the new Result  Code
                view!!.setNewResult(WAProfileImageActivity.RESPONSE_CODE_OPEN_GALLERY)
                Toast.makeText(context, "Open Gallery", Toast.LENGTH_SHORT).show()

            }
            0 -> {
                // TODO open Cam and get the image path and set the new Result  Code
                view!!.setNewResult(WAProfileImageActivity.RESPONSE_CODE_OPEN_CAMERA)
                Toast.makeText(context, "Open Camera", Toast.LENGTH_SHORT).show()

            }
            1 -> {
                // TODO Remove the image and get image path
                view!!.setNewResult(WAProfileImageActivity.RESPONSE_CODE_REMOVE_IMAGE)
                Toast.makeText(context, "Remove Photo", Toast.LENGTH_SHORT).show()

            }
        }


    }

}