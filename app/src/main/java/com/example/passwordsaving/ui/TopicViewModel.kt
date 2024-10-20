package com.example.passwordsaving.ui

import AppDatabase
import Topic
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TopicViewModel(private val database: AppDatabase) : ViewModel() {

    val topics: LiveData<List<Topic>> = database.topicDao().getAllTopics()
}
