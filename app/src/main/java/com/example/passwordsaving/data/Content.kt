package com.example.passwordsaving.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Content")
data class Content(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val topicId: Int,  // 外键指向 Topic
    val topicContent: String,
    val contentDetail: String
)
