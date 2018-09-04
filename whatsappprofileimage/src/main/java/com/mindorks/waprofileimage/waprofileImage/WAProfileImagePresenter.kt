package com.mindorks.waprofileimage.waprofileImage

import android.content.Context
import android.os.Build
import android.support.v4.app.FragmentActivity
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.mindorks.waprofileimage.R
import com.mindorks.waprofileimage.callback.TaskFinished
import com.mindorks.waprofileimage.customdialog.DialogCustomization
import com.mindorks.waprofileimage.customdialog.ListHolder
import com.mindorks.waprofileimage.customdialog.SimpleAdapter
import com.mindorks.waprofileimage.customdialog.callback.*
import com.mindorks.waprofileimage.util.PermUtil

class WAProfileImagePresenter constructor(wAProfileImageView: WAProfileImageView, ctx: Context)
    : WAProfileImagePresnterInterface, OnClickListener,
        OnDismissListener,
        OnItemClickListener, OnCancelListener, OnBackPressListener {


    override fun launchOptionDialog(context: FragmentActivity, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermUtil.checkForCamara_WritePermissions(context, object : TaskFinished {
                override fun onTaskFinished(check: Boolean?) {
                    showCustomeDialog(context, requestCode)
                }
            })
        } else {
            showCustomeDialog(context, requestCode)

        }


    }

    var view: WAProfileImageView? = null
    var context: Context
    var requestCode: Int? = null

    init {
        view = wAProfileImageView
        context = ctx

    }

    override fun onClick(dialog: DialogCustomization, view: View) {

    }

    override fun onDismiss(dialog: DialogCustomization) {
    }

    override fun onItemClick(dialog: DialogCustomization, item: Any, view: View, position: Int) {
        handleEachItemClicked(position)
    }


    override fun onCancel(dialog: DialogCustomization) {

    }

    override fun onBackPressed(dialog: DialogCustomization) {

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
            setOnClickListener(this@WAProfileImagePresenter)
            setOnItemClickListener(this@WAProfileImagePresenter)
            setOnCancelListener(this@WAProfileImagePresenter)
            setOnDismissListener(this@WAProfileImagePresenter)
            setOnBackPressListener(this@WAProfileImagePresenter)
            setExpanded(true, 450)
            setOverlayBackgroundResource(android.R.color.transparent)

        }

        builder.create().show()


    }

    private fun handleEachItemClicked(position: Int) {
        when (position) {
            -1 -> Toast.makeText(context, "Open Gallery", Toast.LENGTH_SHORT).show()
            0 -> Toast.makeText(context, "Open Camera", Toast.LENGTH_SHORT).show()
            1 -> Toast.makeText(context, "Remove Photo", Toast.LENGTH_SHORT).show()
        }


    }

}

