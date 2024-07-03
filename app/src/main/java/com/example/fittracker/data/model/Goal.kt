package com.example.fittracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weekly_goal")
data class Goal(
    @PrimaryKey val id: Int = 1,
    val goal: Int
)