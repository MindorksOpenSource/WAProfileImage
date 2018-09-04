package com.mindorks.waprofileimage.customdialog

import android.app.Activity
import android.content.Context
import android.view.Display
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.FrameLayout

import com.mindorks.waprofileimage.R
import com.mindorks.waprofileimage.customdialog.callback.*
import com.mindorks.waprofileimage.customdialog.dialogutil.Utils

class DialogCustomization internal constructor(builder: DialogBuilder) {

    /**
     * DialogCustomization base layout root view
     */
    private val rootView: ViewGroup

    /**
     * DialogCustomization content container which is a different layout rather than base layout
     */
    private val contentContainer: ViewGroup

    /**
     * Determines whether dialog should be dismissed by back button or touch in the black overlay
     */
    private val isCancelable: Boolean

    /**
     * Determines whether dialog is showing dismissing animation and avoid to repeat it
     */
    private var isDismissing: Boolean = false

    /**
     * Listener for the user to take action by clicking any item
     */
    private val onItemClickListener: OnItemClickListener?

    /**
     * Listener for the user to take action by clicking views in header or footer
     */
    private val onClickListener: OnClickListener?

    /**
     * Listener to notify the user that dialog has been dismissed
     */
    private val onDismissListener: OnDismissListener?

    /**
     * Listener to notify the user that dialog has been canceled
     */
    lateinit var  onCancelListener: OnCancelListener

    /**
     * Listener to notify back press
     */
    private val onBackPressListener: OnBackPressListener?

    /**
     * Content
     */
    private val holder: Holder

    /**
     * basically activity root view
     */
    private val decorView: ViewGroup

    private val outAnim: Animation
    private val inAnim: Animation

    /**
     * Checks if the dialog is shown
     */
    val isShowing: Boolean
        get() {
            val view = decorView.findViewById<View>(R.id.dialogcustomization_outmost_container)
            return view != null
        }

    /**
     * Returns header view if it was set.
     */
    val headerView: View?
        get() = holder.header

    /**
     * Returns footer view if it was set.
     */
    val footerView: View?
        get() = holder.footer

    /**
     * Returns holder view.
     */
    val holderView: View
        get() = holder.inflatedView

    /**
     * Called when the user touch on black overlay in order to dismiss the dialog
     */
    private val onCancelableTouchListener = View.OnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            onCancelListener.onCancel(this@DialogCustomization)
            dismiss()
        }
        false
    }

    init {
        val layoutInflater = LayoutInflater.from(builder.context)

        val activity = builder.context as Activity?

        holder = builder.getHolder()

        onItemClickListener = builder.getOnItemClickListener()
        onClickListener = builder.getOnClickListener()
        onDismissListener = builder.getOnDismissListener()
        onCancelListener = builder.getOnCancelListener()!!
        onBackPressListener = builder.getOnBackPressListener()
        isCancelable = builder.isCancelable()

        /*
     * Avoid getting directly from the decor view because by doing that we are overlapping the black soft key on
     * nexus device. I think it should be tested on different devices but in my opinion is the way to go.
     * @link http://stackoverflow.com/questions/4486034/get-root-view-from-current-activity
     */
        decorView = activity!!.window.decorView.findViewById(android.R.id.content)
        rootView = layoutInflater.inflate(R.layout.base_container, decorView, false) as ViewGroup
        rootView.layoutParams = builder.outmostLayoutParams

        val outmostView = rootView.findViewById<View>(R.id.dialogcustomization_outmost_container)
        outmostView.setBackgroundResource(builder.getOverlayBackgroundResource())

        contentContainer = rootView.findViewById(R.id.dialogcustomization_content_container)
        contentContainer.layoutParams = builder.contentParams

        outAnim = builder.getOutAnimation()
        inAnim = builder.getInAnimation()

        initContentView(
                layoutInflater,
                builder.getHeaderView(),
                builder.isFixedHeader,
                builder.getFooterView(),
                builder.isFixedFooter,
                builder.getAdapter(),
                builder.contentPadding,
                builder.contentMargin
        )

        initCancelable()
        if (builder.isExpanded()) {
            initExpandAnimator(activity, builder.getDefaultContentHeight(), builder.contentParams.gravity)
        }
    }

    /**
     * Displays the dialog if it is not shown already.
     */
    fun show() {
        if (isShowing) {
            return
        }
        onAttached(rootView)
    }

    /**
     * Dismisses the displayed dialog.
     */
    fun dismiss() {
        if (isDismissing) {
            return
        }

        outAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                decorView.post {
                    decorView.removeView(rootView)
                    isDismissing = false
                    onDismissListener?.onDismiss(this@DialogCustomization)
                }
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        contentContainer.startAnimation(outAnim)
        isDismissing = true
    }

    /**
     * Checks the given resource id and return the corresponding view if it exists.
     *
     * @return null if it is not found
     */
    fun findViewById(resourceId: Int): View? {
        return contentContainer.findViewById(resourceId)
    }

    private fun initExpandAnimator(activity: Activity, defaultHeight: Int, gravity: Int) {
        var defaultHeight = defaultHeight
        val display = activity.windowManager.defaultDisplay
        val displayHeight = display.height - Utils.getStatusBarHeight(activity)
        if (defaultHeight == 0) {
            defaultHeight = displayHeight * 2 / 5
        }

        val view = holder.inflatedView as? AbsListView ?: return

        view.setOnTouchListener(ExpandTouchListener.newListener(
                activity, view, contentContainer, gravity, displayHeight, defaultHeight
        ))
    }

    /**
     * It is called in order to create content
     */
    private fun initContentView(inflater: LayoutInflater, header: View?, fixedHeader: Boolean, footer: View?,
                                fixedFooter: Boolean, adapter: BaseAdapter?, padding: IntArray, margin: IntArray) {
        val contentView = createView(inflater, header, fixedHeader, footer, fixedFooter, adapter)
        val params = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        params.setMargins(margin[0], margin[1], margin[2], margin[3])
        contentView.layoutParams = params
        holderView.setPadding(padding[0], padding[1], padding[2], padding[3])
        contentContainer.addView(contentView)
    }

    /**
     * It is called to set whether the dialog is cancellable by pressing back button or
     * touching the black overlay
     */
    private fun initCancelable() {
        if (!isCancelable) {
            return
        }
        val view = rootView.findViewById<View>(R.id.dialogcustomization_outmost_container)
        view.setOnTouchListener(onCancelableTouchListener)
    }

    /**
     * it is called when the content view is created
     *
     * @param inflater used to inflate the content of the dialog
     * @return any view which is passed
     */
    private fun createView(inflater: LayoutInflater, headerView: View?, fixedHeader: Boolean,
                           footerView: View?, fixedFooter: Boolean, adapter: BaseAdapter?): View {
        val view = holder.getView(inflater, rootView)

        if (holder is ViewHolder) {
            assignClickListenerRecursively(view)
        }

        if (headerView != null) {
            assignClickListenerRecursively(headerView)
            holder.addHeader(headerView, fixedHeader)
        }

        if (footerView != null) {
            assignClickListenerRecursively(footerView)
            holder.addFooter(footerView, fixedFooter)
        }

        if (adapter != null && holder is HolderAdapter) {
            val holderAdapter = holder
            holderAdapter.setAdapter(adapter)
            holderAdapter.setOnItemClickListener(object : OnHolderListener {
                override fun onItemClick(item: Any, view: View, position: Int) {
                    if (onItemClickListener == null) {
                        return
                    }
                    onItemClickListener.onItemClick(this@DialogCustomization, item, view, position)
                }
            })
        }
        return view
    }

    /**
     * Loop among the views in the hierarchy and assign listener to them
     */
    private fun assignClickListenerRecursively(parent: View?) {
        if (parent == null) {
            return
        }

        if (parent is ViewGroup) {
            val viewGroup = parent as ViewGroup?
            val childCount = viewGroup!!.childCount
            for (i in childCount - 1 downTo 0) {
                val child = viewGroup.getChildAt(i)
                assignClickListenerRecursively(child)
            }
        }
        setClickListener(parent)
    }

    /**
     * It is used to setListener on view that have a valid id associated
     */
    private fun setClickListener(view: View) {
        if (view.id == INVALID) {
            return
        }
        //adapterview does not support click listener
        if (view is AdapterView<*>) {
            return
        }

        view.setOnClickListener(View.OnClickListener { v ->
            if (onClickListener == null) {
                return@OnClickListener
            }
            onClickListener.onClick(this@DialogCustomization, v)
        })
    }

    /**
     * It is called when the show() method is called
     *
     * @param view is the dialog plus view
     */
    private fun onAttached(view: View) {
        decorView.addView(view)
        contentContainer.startAnimation(inAnim)

        contentContainer.requestFocus()
        holder.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            when (event.action) {
                KeyEvent.ACTION_UP -> if (keyCode == KeyEvent.KEYCODE_BACK) {
                    onBackPressListener?.onBackPressed(this@DialogCustomization)
                    if (isCancelable) {
                        onBackPressed(this@DialogCustomization)
                    }
                    return@OnKeyListener true
                }
                else -> {
                }
            }
            false
        })
    }

    /**
     * Invoked when back button is pressed. Automatically dismiss the dialog.
     */
    fun onBackPressed(dialogCustomization: DialogCustomization) {
        onCancelListener?.onCancel(this@DialogCustomization)
        dismiss()
    }

    companion object {

        private val INVALID = -1

        /**
         * Creates a new dialog builder
         */
        fun newDialog(context: Context): DialogBuilder {
            return DialogBuilder(context)
        }
    }
}
