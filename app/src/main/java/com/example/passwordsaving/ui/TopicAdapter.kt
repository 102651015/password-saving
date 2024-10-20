package com.example.passwordsaving.ui

import Topic
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordsaving.R

class TopicAdapter(private var items: List<Topic>) :
    RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_topic, parent, false)
        return TopicViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    inner class TopicViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val topicText: TextView = view.findViewById(R.id.topicText)
        private val subtopicText: TextView = view.findViewById(R.id.subtopicText)

        fun bind(item: Topic) {
            topicText.text = item.topic
            subtopicText.text = item.subtopic
        }
    }

    // 更新数据并通知 RecyclerView
    fun updateItems(newItems: List<Topic>) {
        this.items = newItems
        notifyDataSetChanged()  // 通知数据更新
    }
}
