package com.example.passwordsaving.ui

import AppDatabase
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.passwordsaving.R
import com.example.passwordsaving.databinding.FragmentViewBinding

class ViewFragment : Fragment(R.layout.fragment_view) {

    private lateinit var adapter: TopicAdapter
    private lateinit var binding: FragmentViewBinding
    private lateinit var topicViewModel: TopicViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("ViewFragment", "onCreateView started")
        binding = FragmentViewBinding.inflate(inflater, container, false)

        // 初始化 RecyclerView 和 Adapter
        adapter = TopicAdapter(emptyList())
        binding.passwordList.layoutManager = LinearLayoutManager(requireContext())
        binding.passwordList.adapter = adapter

        // 初始化数据库
        val database = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "app_database"
        ).build()

        // 初始化 ViewModel 通过 ViewModelFactory
        val factory = TopicViewModelFactory(database)
        topicViewModel = ViewModelProvider(this, factory).get(TopicViewModel::class.java)

        // 观察数据库中的数据
        topicViewModel.topics.observe(viewLifecycleOwner) { topics ->
            Log.d("ViewFragment", "Topics: $topics")
            if (topics.isEmpty()) {
                Log.d("ViewFragment", "No topics found, navigating to AddFragment")
                // 如果数据库中没有数据，跳转到 AddFragment 来添加数据
                findNavController().navigate(R.id.action_viewFragment_to_addFragment)
            } else {
                adapter.updateItems(topics)
            }
        }

        return binding.root
    }
}

