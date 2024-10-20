package com.example.passwordsaving.ui

import AppDatabase
import Topic
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.passwordsaving.R
import com.example.passwordsaving.data.Content
import com.example.passwordsaving.databinding.FragmentAddBinding
import kotlinx.coroutines.launch

class AddFragment : Fragment(R.layout.fragment_add) {

    private lateinit var labelViewModel: LabelViewModel
    private lateinit var adapter: LabelAdapter
    private lateinit var binding: FragmentAddBinding
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 使用 DataBinding 来绑定布局
        binding = FragmentAddBinding.inflate(inflater, container, false)
        // 初始化 RecyclerView 及其 Adapter
        adapter = LabelAdapter(emptyList())
        binding.itemList.layoutManager = LinearLayoutManager(requireContext())
        binding.itemList.adapter = adapter

        // 观察 LiveData 的变化更新 RecyclerView
        labelViewModel = ViewModelProvider(this).get(LabelViewModel::class.java)// 获取 ViewModel 实例
        labelViewModel.labelItems.observe(viewLifecycleOwner) { labelItems ->
            adapter.updateItems(labelItems)
        }

        binding.addItem.setOnClickListener {
            addNewLabelItem() // 调用方法添加新项
        }
        Log.d("AddFragment", "Attempting to initialize database")
        // 初始化 Room 数据库
        db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "app_database"
        ).build()
        Log.d("AddFragment", "Database initialized successfully")

        // 处理保存按钮的点击事件
        binding.save.setOnClickListener {
            saveToDatabase()  // 保存数据到数据库
        }

        return binding.root
    }

    // 新的添加方法
    private fun addNewLabelItem() {
        // 确保最后一项 EditText 填满
        val itemCount = binding.itemList.childCount
        for (i in 0 until itemCount) {
            val viewHolder = binding.itemList.getChildAt(i)
            val topicEditText = viewHolder.findViewById<EditText>(R.id.topicEditText)
            val contentEditText = viewHolder.findViewById<EditText>(R.id.contentEditText)

            // 如果任意一个 EditText 为空，不允许添加新项
            if (topicEditText.text.isEmpty() || contentEditText.text.isEmpty()) {
                return
            }
        }

        // 添加新项到 ViewModel
        labelViewModel.addLabel("Label", "", "")

        // 获取更新后的 labelItems 列表并更新 adapter
        val updatedItems = labelViewModel.labelItems.value ?: emptyList()
        adapter.updateItems(updatedItems)

        // 滚动到新添加的项
        binding.itemList.scrollToPosition(updatedItems.size - 1)
    }

    private fun saveToDatabase() {
        // 确保 itemList 有至少一个 item
        if (binding.itemList.childCount == 0) {
            return
        }

        val firstItem = binding.itemList.getChildAt(0)
        val topicEditText = firstItem.findViewById<EditText>(R.id.topicEditText)
        val contentEditText = firstItem.findViewById<EditText>(R.id.contentEditText)

        // 获取第一个 item 的 EditText 内容
        val topic = topicEditText.text.toString()
        val subtopic = contentEditText.text.toString()

        // 异步保存到数据库
        lifecycleScope.launch {
            // 插入 Topic 并获取新生成的 topicId
            val topicId = db.topicDao().insertTopic(Topic(topic = topic, subtopic = subtopic))

            // 遍历剩下的 item，并将它们插入 Content 表
            for (i in 1 until binding.itemList.childCount) {
                val viewHolder = binding.itemList.getChildAt(i)
                val topicContentEditText = viewHolder.findViewById<EditText>(R.id.topicEditText)
                val contentDetailEditText = viewHolder.findViewById<EditText>(R.id.contentEditText)

                val topicContent = topicContentEditText.text.toString()
                val contentDetail = contentDetailEditText.text.toString()

                // 插入到 Content 表
                db.contentDao().insertContent(Content(topicId = topicId.toInt(), topicContent = topicContent, contentDetail = contentDetail))
            }
            // 显示保存成功的反馈
            Toast.makeText(requireContext(), "Data saved successfully", Toast.LENGTH_SHORT).show()
            Log.d("AddFragment", "Saving topic: $topic with subtopic: $subtopic")
        }
    }

}

