package com.seweryn.dazncodechallenge.ui.main.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seweryn.dazncodechallenge.R
import com.seweryn.dazncodechallenge.ui.extensions.showConditionally
import com.seweryn.dazncodechallenge.viewmodel.main.MainViewModel.ContentItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_event.view.*

class MainListRecyclerAdapter : RecyclerView.Adapter<MainListRecyclerAdapter.ViewHolder>() {

    private var content: List<ContentItem> = mutableListOf()

    fun updateEvents(content: List<ContentItem>) {
        this.content = content
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = content.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contentItem = content[position], isLastPosition = isItemOnLastPosition(position))
    }

    private fun isItemOnLastPosition(position: Int) = position == itemCount - 1

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(contentItem: ContentItem, isLastPosition: Boolean) {
            itemView.apply {
                content_title.text = contentItem.title
                content_subtitle.text = contentItem.subtitle
                content_date.text = contentItem.date
                content_divider.showConditionally(!isLastPosition)
                Picasso.get()
                    .load(contentItem.imageUrl)
                    .error(context.getDrawable(R.drawable.error_thumbnail_placeholder))
                    .into(content_thumbnail)
                setOnClickListener { contentItem.selectAction.invoke() }
            }
        }
    }
}