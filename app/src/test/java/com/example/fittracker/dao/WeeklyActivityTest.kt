package com.example.fittracker.dao

import com.example.fittracker.data.model.MonthlyActivity
import org.junit.Assert.assertEquals
import org.junit.Test

class WeeklyActivityTest {
    @Test
    fun `WeeklyActivity model stores and retrieves data correctly`() {
        val totalDuration = 3600
        val week = "2024-10"

        val activity = MonthlyActivity(totalDuration, week)

        assertEquals(totalDuration, activity.totalDuration)
        assertEquals(week, activity.month)
    }
}
