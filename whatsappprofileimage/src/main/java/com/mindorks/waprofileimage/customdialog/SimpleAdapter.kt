package com.mindorks.waprofileimage.customdialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.mindorks.waprofileimage.R

class SimpleAdapter(
    context: Context,
    private val count: Int) : BaseAdapter() {

  private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

  override fun getCount(): Int {
    return count
  }

  override fun getItem(position: Int): Any {
    return position
  }

  override fun getItemId(position: Int): Long {
    return position.toLong()
  }

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val viewHolder: ViewHolder
    var view: View? = convertView

    if (view == null) {
      view = layoutInflater.inflate(R.layout.item_dialog, parent, false)

      viewHolder = ViewHolder(
          view.findViewById(R.id.text_view),
          view.findViewById(R.id.image_view)
      )
      view.tag = viewHolder
    } else {
      viewHolder = view.tag as ViewHolder
    }

    val context = parent.context
    when (position) {
      0 -> {
        viewHolder.textView.text = context.getString(R.string.gallery_title)
       // viewHolder.imageView.setImageResource(R.drawable.ic_google_plus_icon)
      }
      1 -> {
        viewHolder.textView.text = context.getString(R.string.camera_title)
       // viewHolder.imageView.setImageResource(R.drawable.ic_google_maps_icon)
      }
      else -> {
        viewHolder.textView.text = context.getString(R.string.remove_photo_title)
      //  viewHolder.imageView.setImageResource(R.drawable.ic_google_messenger_icon)
      }
    }

    return view!!
  }

  data class ViewHolder(val textView: TextView, val imageView: ImageView)
}
