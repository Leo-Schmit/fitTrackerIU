package com.example.fittracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fittracker.data.model.Activity
import com.example.fittracker.data.model.MonthlyActivity

@Dao
interface ActivityDao {
    @Insert
    suspend fun insertActivity(activity: Activity)

    @Query("SELECT * FROM daily_activities WHERE date(timestamp / 1000, 'unixepoch', 'localtime') = date('now', 'localtime') ORDER BY id DESC")
    suspend fun getTodayActivities(): List<Activity>

    @Query("SELECT SUM(duration) as totalDuration, strftime('%Y-%m', datetime(timestamp / 1000, 'unixepoch')) as month FROM daily_activities GROUP BY month")
    suspend fun getMonthlyActivities(): List<MonthlyActivity>
}

