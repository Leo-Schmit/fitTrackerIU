package com.example.fittracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_activities")
data class Activity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val duration: Int,
    val timestamp: Long
)
