package com.example.fittracker.dao

import com.example.fittracker.data.dao.GoalDao
import com.example.fittracker.data.model.Goal
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GoalDaoTest {
    @Mock
    private lateinit var goalDao: GoalDao

    @Test
    fun `insertOrUpdateGoal inserts a new goal correctly`() = runTest {
        val goal = Goal(id = 1, goal = 10000)
        `when`(goalDao.insertOrUpdateGoal(goal)).thenReturn(1L)

        val result = goalDao.insertOrUpdateGoal(goal)

        verify(goalDao).insertOrUpdateGoal(goal)
        assertEquals(1L, result)
    }

    @Test
    fun `getGoal retrieves the correct goal`() = runTest {
        val goal = Goal(id = 1, goal = 10000)
        `when`(goalDao.getGoal()).thenReturn(goal)

        val fetchedGoal = goalDao.getGoal()

        verify(goalDao).getGoal()
        assertNotNull("Goal should not be null", fetchedGoal)
        assertEquals("Goal ID should match", 1, fetchedGoal?.id)
        assertEquals("Goal target steps should match", 10000, fetchedGoal?.goal)
    }
}
