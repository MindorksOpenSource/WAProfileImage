package com.mindorks.waprofileimage.customdialog

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.BaseAdapter
import android.widget.FrameLayout

import com.mindorks.waprofileimage.R
import com.mindorks.waprofileimage.customdialog.callback.OnBackPressListener
import com.mindorks.waprofileimage.customdialog.callback.OnCancelListener
import com.mindorks.waprofileimage.customdialog.callback.OnClickListener
import com.mindorks.waprofileimage.customdialog.callback.OnDismissListener
import com.mindorks.waprofileimage.customdialog.callback.OnItemClickListener
import com.mindorks.waprofileimage.customdialog.dialogutil.Utils

import java.util.Arrays

class DialogBuilder {

    private val margin = IntArray(4)
    val contentPadding = IntArray(4)
    private val outMostMargin = IntArray(4)
    private val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
    )

    private var adapter: BaseAdapter? = null
    var context: Context? = null
    private var footerView: View? = null
    private var headerView: View? = null
    private var holder: Holder? = null
    private var gravity = Gravity.BOTTOM
    private var onItemClickListener: OnItemClickListener? = null
    private var onClickListener: OnClickListener? = null
    private var onDismissListener: OnDismissListener? = null
    private var onCancelListener: OnCancelListener? = null
    private var onBackPressListener: OnBackPressListener? = null

    private var isCancelable = true
    private var contentBackgroundResource = android.R.color.white
    private var headerViewResourceId = INVALID
    var isFixedHeader = false
        private set
    private var footerViewResourceId = INVALID
    var isFixedFooter = false
        private set
    private var inAnimation = INVALID
    private var outAnimation = INVALID
    private var expanded: Boolean = false
    private var defaultContentHeight: Int = 0
    private var overlayBackgroundResource = R.color.dialogcustomization_black_overlay

    val contentParams: FrameLayout.LayoutParams
        get() {
            if (expanded) {
                params.height = getDefaultContentHeight()
            }
            return params
        }

    val outmostLayoutParams: FrameLayout.LayoutParams
        get() {
            val params = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            params.setMargins(outMostMargin[0], outMostMargin[1], outMostMargin[2], outMostMargin[3])
            return params
        }

    val contentMargin: IntArray
        get() {
            val minimumMargin = context!!.resources.getDimensionPixelSize(
                    R.dimen.dialogcustomization_default_center_margin)
            for (i in margin.indices) {
                margin[i] = getMargin(this.gravity, margin[i], minimumMargin)
            }
            return margin
        }

    private constructor() {}

    /**
     * Initialize the builder with a valid context in order to inflate the dialog
     */
    internal constructor(context: Context) {
        this.context = context
        Arrays.fill(margin, INVALID)
    }

    /**
     * Set the adapter that will be used when ListHolder or GridHolder are passed
     */
    fun setAdapter(adapter: BaseAdapter): DialogBuilder {
        this.adapter = adapter
        return this
    }

    /**
     * Set the footer view using the id of the layout resource
     *
     * @param fixed is used to determine whether footer should be fixed or not. Fixed if true, scrollable otherwise
     */
    @JvmOverloads
    fun setFooter(resourceId: Int, fixed: Boolean = false): DialogBuilder {
        this.footerViewResourceId = resourceId
        this.isFixedFooter = fixed
        return this
    }

    /**
     * Sets the given view as footer.
     *
     * @param fixed is used to determine whether footer should be fixed or not. Fixed if true, scrollable otherwise
     */
    @JvmOverloads
    fun setFooter(view: View, fixed: Boolean = false): DialogBuilder {
        this.footerView = view
        this.isFixedFooter = fixed
        return this
    }

    /**
     * Set the header view using the id of the layout resource
     *
     * @param fixed is used to determine whether header should be fixed or not. Fixed if true, scrollable otherwise
     */
    @JvmOverloads
    fun setHeader(resourceId: Int, fixed: Boolean = false): DialogBuilder {
        this.headerViewResourceId = resourceId
        this.isFixedHeader = fixed
        return this
    }

    /**
     * Set the header view using a view
     *
     * @param fixed is used to determine whether header should be fixed or not. Fixed if true, scrollable otherwise
     */
    @JvmOverloads
    fun setHeader(view: View, fixed: Boolean = false): DialogBuilder {
        this.headerView = view
        this.isFixedHeader = fixed
        return this
    }
    //DialogBuilder
    /**
     * Define if the dialog is cancelable and should be closed when back pressed or click outside is pressed
     */
    fun setCancelable(isCancelable: Boolean): DialogBuilder {
        this.isCancelable = isCancelable
        return this
    }

    /**
     * Set the content of the dialog by passing one of the provided Holders
     */
    fun setContentHolder(holder: Holder): DialogBuilder {
        this.holder = holder
        return this
    }

    /**
     * Use setBackgroundResource
     */
    @Deprecated("")
    fun setBackgroundColorResId(resourceId: Int): DialogBuilder {
        return setContentBackgroundResource(resourceId)
    }

    /**
     * Set background color for your dialog. If no resource is passed 'white' will be used
     */
    fun setContentBackgroundResource(resourceId: Int): DialogBuilder {
        this.contentBackgroundResource = resourceId
        return this
    }

    fun setOverlayBackgroundResource(resourceId: Int): DialogBuilder {
        this.overlayBackgroundResource = resourceId
        return this
    }

    /**
     * Set the gravity you want the dialog to have among the ones that are provided
     */
    fun setGravity(gravity: Int): DialogBuilder {
        this.gravity = gravity
        params.gravity = gravity
        return this
    }

    /**
     * Customize the in animation by passing an animation resource
     */
    fun setInAnimation(inAnimResource: Int): DialogBuilder {
        this.inAnimation = inAnimResource
        return this
    }

    /**
     * Customize the out animation by passing an animation resource
     */
    fun setOutAnimation(outAnimResource: Int): DialogBuilder {
        this.outAnimation = outAnimResource
        return this
    }

    /**
     * Add margins to your outmost view which contains everything. As default they are 0
     * are applied
     */
    fun setOutMostMargin(left: Int, top: Int, right: Int, bottom: Int): DialogBuilder {
        this.outMostMargin[0] = left
        this.outMostMargin[1] = top
        this.outMostMargin[2] = right
        this.outMostMargin[3] = bottom
        return this
    }

    /**
     * Add margins to your dialog. They are set to 0 except when gravity is center. In that case basic margins
     * are applied
     */
    fun setMargin(left: Int, top: Int, right: Int, bottom: Int): DialogBuilder {
        this.margin[0] = left
        this.margin[1] = top
        this.margin[2] = right
        this.margin[3] = bottom
        return this
    }

    /**
     * Set paddings for the dialog content
     */
    fun setPadding(left: Int, top: Int, right: Int, bottom: Int): DialogBuilder {
        this.contentPadding[0] = left
        this.contentPadding[1] = top
        this.contentPadding[2] = right
        this.contentPadding[3] = bottom
        return this
    }

    /**
     * Set an item click listener when list or grid holder is chosen. In that way you can have callbacks when one
     * of your items is clicked
     */
    fun setOnItemClickListener(listener: OnItemClickListener): DialogBuilder {
        this.onItemClickListener = listener
        return this
    }

    /**
     * Set a global click listener to you dialog in order to handle all the possible click events. You can then
     * identify the view by using its id and handle the correct behaviour
     */
    fun setOnClickListener(listener: OnClickListener): DialogBuilder {
        this.onClickListener = listener
        return this
    }

    fun setOnDismissListener(listener: OnDismissListener?): DialogBuilder {
        this.onDismissListener = listener
        return this
    }

    fun setOnCancelListener(listener: OnCancelListener?): DialogBuilder {
        this.onCancelListener = listener
        return this
    }

    fun setOnBackPressListener(listener: OnBackPressListener?): DialogBuilder {
        this.onBackPressListener = listener
        return this
    }

    fun setExpanded(expanded: Boolean): DialogBuilder {
        this.expanded = expanded
        return this
    }

    fun setExpanded(expanded: Boolean, defaultContentHeight: Int): DialogBuilder {
        this.expanded = expanded
        this.defaultContentHeight = defaultContentHeight
        return this
    }

    fun setContentHeight(height: Int): DialogBuilder {
        params.height = height
        return this
    }

    fun setContentWidth(width: Int): DialogBuilder {
        params.width = width
        return this
    }

    /**
     * Create the dialog using this builder
     */
    fun create(): DialogCustomization {
        getHolder().setBackgroundResource(getContentBackgroundResource())
        return DialogCustomization(this)
    }

    fun getFooterView(): View? {
        return Utils.getView(this.context!!, footerViewResourceId, footerView)
    }

    fun getHeaderView(): View? {
        return Utils.getView(this.context!!, headerViewResourceId, headerView)
    }

    fun getHolder(): Holder {
        if (holder == null) {
            holder = ListHolder()
        }
        return holder as Holder
    }

    fun getAdapter(): BaseAdapter? {
        return adapter
    }

    fun getInAnimation(): Animation {
        val res = if (inAnimation == INVALID) Utils.getAnimationResource(this.gravity, true) else inAnimation
        return AnimationUtils.loadAnimation(context, res)
    }

    fun getOutAnimation(): Animation {
        val res = if (outAnimation == INVALID) Utils.getAnimationResource(this.gravity, false) else outAnimation
        return AnimationUtils.loadAnimation(context, res)
    }

    fun isExpanded(): Boolean {
        return expanded
    }

    fun isCancelable(): Boolean {
        return isCancelable
    }

    fun getOnItemClickListener(): OnItemClickListener? {
        return onItemClickListener
    }

    fun getOnClickListener(): OnClickListener? {
        return onClickListener
    }

    fun getOnDismissListener(): OnDismissListener? {
        return onDismissListener
    }

    fun getOnCancelListener(): OnCancelListener? {
        return onCancelListener
    }

    fun getOnBackPressListener(): OnBackPressListener? {
        return onBackPressListener
    }

    fun getDefaultContentHeight(): Int {
        val activity = context as Activity
        val display = activity.windowManager.defaultDisplay
        val displayHeight = display.height - Utils.getStatusBarHeight(activity)
        if (defaultContentHeight == 0) {
            defaultContentHeight = displayHeight * 2 / 5
        }
        return defaultContentHeight
    }

    fun getOverlayBackgroundResource(): Int {
        return overlayBackgroundResource
    }

    fun getContentBackgroundResource(): Int {
        return contentBackgroundResource
    }

    /**
     * Get margins if provided or assign default values based on gravity
     *
     * @param gravity       the gravity of the dialog
     * @param margin        the value defined in the builder
     * @param minimumMargin the minimum margin when gravity center is selected
     * @return the value of the margin
     */
    private fun getMargin(gravity: Int, margin: Int, minimumMargin: Int): Int {
        when (gravity) {
            Gravity.CENTER -> return if (margin == INVALID) minimumMargin else margin
            else -> return if (margin == INVALID) 0 else margin
        }
    }

    companion object {
        private val INVALID = -1
    }
}
/**
 * Set the footer view using the id of the layout resource
 */
/**
 * Sets the given view as footer.
 */
/**
 * Set the header view using the id of the layout resource
 */
/**
 * Set the header view using a view
 */
