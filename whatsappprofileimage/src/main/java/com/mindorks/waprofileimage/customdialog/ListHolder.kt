package com.mindorks.waprofileimage.customdialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.ListView
import com.mindorks.waprofileimage.R

class ListHolder : HolderAdapter, AdapterView.OnItemClickListener {
    private var backgroundResource: Int = 0

    private var listView: ListView? = null
    private var listener: OnHolderListener? = null
    private var keyListener: View.OnKeyListener? = null
    private var headerView: View? = null
    private var footerView: View? = null
    private var footerContainer: ViewGroup? = null
    private var headerContainer: ViewGroup? = null

    override fun addHeader(view: View) {
        addHeader(view, false)
    }

    override fun addHeader(view: View, fixed: Boolean) {
        if (fixed) {
            headerContainer!!.addView(view)
        } else {
            listView!!.addHeaderView(view)
        }
        headerView = view
    }

    override fun addFooter(view: View) {
        addFooter(view, false)
    }

    override fun addFooter(view: View, fixed: Boolean) {
        if (fixed) {
            footerContainer!!.addView(view)
        } else {
            listView!!.addFooterView(view)
        }
        footerView = view
    }

    override fun setAdapter(adapter: BaseAdapter) {
        listView!!.adapter = adapter
    }

    override fun setBackgroundResource(colorResource: Int) {
        this.backgroundResource = colorResource
    }

    override fun getView(inflater: LayoutInflater, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.dialog_list, parent, false)
        val outMostView = view.findViewById<LinearLayout>(R.id.dialogcustomization_outmost_container)
        outMostView.setBackgroundResource(backgroundResource)
        listView = view.findViewById(R.id.dialogcustomization_list)
        listView!!.onItemClickListener = this
        listView!!.setOnKeyListener { v, keyCode, event ->
            if (keyListener == null) {
                throw NullPointerException("keyListener should not be null")
            }
            keyListener!!.onKey(v, keyCode, event)
        }
        headerContainer = view.findViewById(R.id.dialogcustomization_header_container)
        footerContainer = view.findViewById(R.id.dialogcustomization_footer_container)
        return view
    }

    override fun setOnItemClickListener(listener: OnHolderListener) {
        this.listener = listener
    }

    override fun setOnKeyListener(keyListener: View.OnKeyListener) {
        this.keyListener = keyListener
    }

    override val inflatedView: View
        get() = this.listView!!

    override val header: View?
        get() = headerView
    override val footer: View?
        get() = footerView

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        var position = position
        if (listener == null) {
            return
        }
        //ListView counts header as position as well. For consistency we don't
        listener!!.onItemClick(
                parent.getItemAtPosition(position),
                view,
                if (headerView != null) --position else position
        )
    }
}