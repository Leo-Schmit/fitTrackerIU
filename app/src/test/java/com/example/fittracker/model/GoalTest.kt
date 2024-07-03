package com.example.fittracker.model

import com.example.fittracker.data.model.Goal
import org.junit.Assert.assertEquals
import org.junit.Test

class GoalTest {
    @Test
    fun `Goal model stores and retrieves data correctly`() {
        val id = 1
        val goalAmount = 10000

        val goal = Goal(id, goalAmount)

        assertEquals(id, goal.id)
        assertEquals(goalAmount, goal.goal)
    }
}
