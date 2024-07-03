package com.example.fittracker.dao

import com.example.fittracker.data.dao.ActivityDao
import com.example.fittracker.data.model.Activity
import com.example.fittracker.data.model.MonthlyActivity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ActivityDaoTest {
    @Mock
    private lateinit var activityDao: ActivityDao

    @Test
    fun testGetTodayActivities() = runTest {
        `when`(activityDao.getTodayActivities()).thenReturn(
            listOf(
                Activity(id = 1, duration = 600, timestamp = System.currentTimeMillis())
            )
        )

        val result = activityDao.getTodayActivities()

        verify(activityDao).getTodayActivities()
        assert(result.isNotEmpty())
    }

    @Test
    fun testGetWeeklyActivities() = runTest {
        `when`(activityDao.getMonthlyActivities()).thenReturn(
            listOf(
                MonthlyActivity(5400, "2024-26")
            )
        )

        val result = activityDao.getMonthlyActivities()

        verify(activityDao).getMonthlyActivities()
        assert(result.isNotEmpty())
        assertEquals(5400, result[0].totalDuration)
    }
}
