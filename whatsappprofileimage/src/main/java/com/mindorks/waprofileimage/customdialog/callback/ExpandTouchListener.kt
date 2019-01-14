package com.mindorks.waprofileimage.customdialog.callback

import android.content.Context
import android.view.GestureDetector
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.widget.AbsListView
import android.widget.FrameLayout

import com.mindorks.waprofileimage.customdialog.dialogutil.Utils

internal class ExpandTouchListener private constructor(context: Context, private val absListView: AbsListView, private val contentContainer: View, private val gravity: Int,
                                                       /**
                                                        * This is used to determine top. status bar height is removed
                                                        */
                                                       private val displayHeight: Int,
                                                       /**
                                                        * Default height for the holder
                                                        */
                                                       private val defaultContentHeight: Int) : View.OnTouchListener {
    private val gestureDetector: GestureDetector

    /**
     * The last touch position in Y Axis
     */
    private var y: Float = 0.toFloat()

    /**
     * This is used to determine whether dialog reached to top or not.
     */
    private var fullScreen: Boolean = false

    /**
     * This is used to determine whether the user swipes from down to top.
     * touchUp is calculated by touch events
     */
    private var touchUp: Boolean = false

    /**
     * This is used to determine whether the user swipes from down to top.
     * scrollUp is calculated from gesture detector scroll event.
     * This shouldn't be used for the touch events
     */
    private var scrollUp: Boolean = false

    /**
     * Content container params, not the holder itself.
     */
    private val params: FrameLayout.LayoutParams

    init {
        this.params = contentContainer.layoutParams as FrameLayout.LayoutParams

        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                scrollUp = distanceY > 0
                return false
            }
        })
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        //If single tapped, don't consume the event
        if (gestureDetector.onTouchEvent(event)) {
            return false
        }

        // when the dialog is fullscreen and user scrolls the content
        // don't consume the event
        if (!(!scrollUp && Utils.listIsAtTop(absListView)) && fullScreen) {
            return false
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                y = event.rawY
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                // This is a quick fix to not trigger click event
                if (params.height == displayHeight) {
                    params.height--
                    contentContainer.layoutParams = params
                    return false
                }
                onTouchMove(v, event)
            }
            MotionEvent.ACTION_UP -> onTouchUp(v, event)
            else -> {
            }
        }
        return true
    }

    private fun onTouchMove(view: View, event: MotionEvent) {
        // sometimes Action_DOWN is not called, we need to make sure that
        // we calculate correct y value
        if (y == -1f) {
            y = event.rawY
        }
        var delta = y - event.rawY
        // if delta > 0 , that means user swipes to top
        touchUp = delta > 0
        if (gravity == Gravity.TOP) {
            delta = -delta
        }
        //update the y value, otherwise delta will be incorrect
        y = event.rawY

        var newHeight = params.height + delta.toInt()

        // This prevents dialog to move out of screen bounds
        if (newHeight > displayHeight) {
            newHeight = displayHeight
        }

        // This prevents the dialog go below the default value while dragging
        if (newHeight < defaultContentHeight) {
            newHeight = defaultContentHeight
        }
        params.height = newHeight
        contentContainer.layoutParams = params

        // we use fullscreen value to activate view content scroll
        fullScreen = params.height == displayHeight
    }

    private fun onTouchUp(view: View, event: MotionEvent) {
        // reset y value
        y = -1f

        // if the dragging direction is from top to down and dialog position still can't exceeds threshold
        // move the dialog automatically to top
        if (!touchUp && params.height < displayHeight && params.height > displayHeight * 4 / 5) {
            Utils.animateContent(contentContainer, displayHeight, object : SimpleAnimationListener() {
                override fun onAnimationEnd(animation: Animation) {
                    fullScreen = true
                }
            })
            return
        }

        // if the dragging direction is down to top and dialog is dragged more than 50 to up
        // move the dialog automatically to top
        if (touchUp && params.height > defaultContentHeight + 50) {
            Utils.animateContent(contentContainer, displayHeight, object : SimpleAnimationListener() {
                override fun onAnimationEnd(animation: Animation) {
                    fullScreen = true
                }
            })
            return
        }

        // if the dragging direction is from down to top and dialog position still can't exceeds threshold
        // move the dialog automatically to down
        if (touchUp && params.height <= defaultContentHeight + 50) {
            Utils.animateContent(contentContainer, defaultContentHeight, SimpleAnimationListener())
            return
        }

        // if the dragging direction is from top to down and the position exceeded the threshold
        // move the dialog to down
        if (!touchUp && params.height > defaultContentHeight) {
            Utils.animateContent(contentContainer, defaultContentHeight, SimpleAnimationListener())
        }
    }

    companion object {

        fun newListener(context: Context, listView: AbsListView, container: View,
                        gravity: Int, displayHeight: Int, defaultContentHeight: Int): ExpandTouchListener {
            return ExpandTouchListener(context, listView, container, gravity, displayHeight, defaultContentHeight)
        }
    }
}
