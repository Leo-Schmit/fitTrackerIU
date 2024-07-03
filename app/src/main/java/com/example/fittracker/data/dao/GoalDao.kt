package com.example.fittracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fittracker.data.model.Goal

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateGoal(goal: Goal): Long

    @Query("SELECT * FROM weekly_goal WHERE id = 1")
    suspend fun getGoal(): Goal?
}
