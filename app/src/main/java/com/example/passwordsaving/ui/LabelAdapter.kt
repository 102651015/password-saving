package com.example.passwordsaving.ui

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordsaving.R

class LabelAdapter(private var items: List<LabelItem>) :
    RecyclerView.Adapter<LabelAdapter.LabelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_label, parent, false)
        return LabelViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: LabelViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    inner class LabelViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val labelTextView: TextView = view.findViewById(R.id.labelTextView)
        private val topicEditText: EditText = view.findViewById(R.id.topicEditText)
        private val contentEditText: EditText = view.findViewById(R.id.contentEditText)

        // 将所有逻辑封装到 bind 函数中
        fun bind(item: LabelItem) {
            labelTextView.text = item.label
            topicEditText.setText(item.firstInput)
            contentEditText.setText(item.secondInput)
        }
    }

    // 更新数据
    fun updateItems(newItems: List<LabelItem>) {
        this.items = newItems
        notifyItemInserted(items.size - 1)
    }
}


