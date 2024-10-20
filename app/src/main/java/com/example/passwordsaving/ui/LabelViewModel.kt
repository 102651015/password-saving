package com.example.passwordsaving.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// 用于存储 Label 及其两个 EditText 数据的类
data class LabelItem(val label: String, var firstInput: String, var secondInput: String)

class LabelViewModel : ViewModel() {
    // 使用 MutableLiveData 存储 Label 和对应的两个 EditText 数据
    private val _labelItems = MutableLiveData<MutableList<LabelItem>>()
    val labelItems: LiveData<MutableList<LabelItem>> = _labelItems

    init {
        // 初始化默认的 Label 和 EditText 数据
        _labelItems.value = mutableListOf(LabelItem("Topic", "", ""))
    }

    // 添加新的 Label 和两个输入框内容
    fun addLabel(label: String, firstInput: String, secondInput: String) {
        // 获取当前的标签列表并添加新项
            val currentList = _labelItems.value ?: mutableListOf()
            currentList.add(LabelItem(label, firstInput, secondInput))
            // 设置新的值并通知更新
            _labelItems.value = currentList
    }
}

