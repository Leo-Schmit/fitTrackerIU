package com.example.fittracker.model

import com.example.fittracker.data.model.Activity
import org.junit.Assert.assertEquals
import org.junit.Test

class ActivityTest {
    @Test
    fun `Activity model stores and retrieves data correctly`() {
        val id = 1
        val duration = 5000
        val timestamp = System.currentTimeMillis()

        val activity = Activity(id, duration, timestamp)

        assertEquals(id, activity.id)
        assertEquals(duration, activity.duration)
        assertEquals(timestamp, activity.timestamp)
    }
}
