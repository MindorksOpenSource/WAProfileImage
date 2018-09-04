package com.mindorks.waprofileimage.waprofileImage

import android.content.Context
import android.os.Build
import android.support.v4.app.FragmentActivity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.mindorks.waprofileimage.R
import com.mindorks.waprofileimage.callback.TaskFinished
import com.mindorks.waprofileimage.customdialog.DialogCustomization
import com.mindorks.waprofileimage.customdialog.ListHolder
import com.mindorks.waprofileimage.customdialog.SimpleAdapter
import com.mindorks.waprofileimage.customdialog.callback.*
import com.mindorks.waprofileimage.util.PermUtil

class WAProfileImagePresenter constructor(wAProfileImageView: WAProfileImageView, ctx: Context)
    : WAProfileImagePresnterInterface,OnClickListener,
        OnDismissListener,
        OnItemClickListener, OnCancelListener , OnBackPressListener {


    override fun launchOptionDialog(context: FragmentActivity, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermUtil.checkForCamara_WritePermissions(context, object : TaskFinished {
                override fun onTaskFinished(check: Boolean?) {
                    //TODO open Custom Dialog
                    showCustomeDialog(context, requestCode)
                }
            })
        } else {
            //TODO open Custom Dialog
            showCustomeDialog(context, requestCode)

        }


    }

    var view: WAProfileImageView? = null
    lateinit var context: Context

    init {
        view = wAProfileImageView
        context = ctx

    }
    override fun onClick(dialog: DialogCustomization, view: View) {

    }
    override fun onDismiss(dialog: DialogCustomization) {
    }
    override fun onItemClick(dialog: DialogCustomization, item: Any, view: View, position: Int) {
    }
    override fun onCancel(dialog: DialogCustomization) {

    }
    override fun onBackPressed(dialog: DialogCustomization) {

    }


    private fun showCustomeDialog(context: FragmentActivity, requestCode: Int) {
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
          //  setExpanded(expanded)


            /*if (contentHeightInput.text.toString().toInt() != -1) {
                setContentHeight(contentHeightInput.text.toString().toInt())
            } else {
                setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
            }

            if (contentWidthInput.text.toString().toInt() != -1) {
                setContentWidth(800)
            }
*/
            setOverlayBackgroundResource(android.R.color.transparent)


        }

        builder.create().show()


    }

}

