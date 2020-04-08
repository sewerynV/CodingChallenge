package com.seweryn.dazncodechallenge.ui.main.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seweryn.dazncodechallenge.R
import com.seweryn.dazncodechallenge.viewmodel.main.MainViewModel.Content
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_event.view.*

class EventsRecyclerAdapter : RecyclerView.Adapter<EventsRecyclerAdapter.ViewHolder>() {

    private var content: List<Content> = mutableListOf()

    fun updateEvents(content: List<Content>) {
        this.content = content
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = content.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(content = content[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(content: Content) {
            itemView.apply {
                event_title.text = content.title
                event_subtitle.text = content.subtitle
                event_date.text = content.date
                Picasso.get()
                    .load(content.imageUrl)
                    .into(event_thumbnail)
            }
        }
    }
}