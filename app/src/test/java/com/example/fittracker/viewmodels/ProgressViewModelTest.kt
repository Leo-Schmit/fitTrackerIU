package com.example.fittracker.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fittracker.data.database.AppDatabase
import com.example.fittracker.data.dao.ActivityDao
import com.example.fittracker.data.dao.GoalDao
import com.example.fittracker.data.model.MonthlyActivity
import com.example.fittracker.data.model.Goal
import com.example.fittracker.ui.viewmodels.ProgressViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class ProgressViewModelTest {

    @get:Rule
    val instantExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ProgressViewModel
    private lateinit var db: AppDatabase
    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var activityDao: ActivityDao
    private lateinit var goalDao: GoalDao

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        activityDao = mockk()
        goalDao = mockk()

        db = mockk {
            every { activityDao() } returns activityDao
            every { goalDao() } returns goalDao
        }

        viewModel = ProgressViewModel(db)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `loadMonthlyActivitiesAndGoal fetches data correctly`() = runTest {
        val expectedActivities = listOf(MonthlyActivity(totalDuration = 120, month = "June"))
        val expectedGoal = Goal(goal = 150)

        coEvery { activityDao.getMonthlyActivities() } returns expectedActivities
        coEvery { goalDao.getGoal() } returns expectedGoal

        viewModel.loadMonthlyActivitiesAndGoal()

        advanceUntilIdle()

        assertEquals(expectedActivities, viewModel.monthlyActivities.value)
        assertEquals(expectedGoal.goal.toFloat(), viewModel.currentGoal.value)
    }
}