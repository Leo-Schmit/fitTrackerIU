package com.example.fittracker.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fittracker.data.database.AppDatabase
import com.example.fittracker.data.dao.ActivityDao
import com.example.fittracker.data.model.Activity
import com.example.fittracker.ui.viewmodels.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.lifecycle.Observer
import io.mockk.coVerify
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel
    private lateinit var database: AppDatabase
    private lateinit var activityDao: ActivityDao
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        activityDao = mockk(relaxed = true)
        database = mockk()
        coEvery { database.activityDao() } returns activityDao

        viewModel = HomeViewModel(database)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `loadActivities loads data into activities LiveData`() = runTest {
        val expectedActivities = listOf(Activity(1, 60, System.currentTimeMillis()))
        coEvery { activityDao.getTodayActivities() } returns expectedActivities

        val observer = mockk<Observer<List<Activity>>>(relaxed = true)
        viewModel.activities.observeForever(observer)

        viewModel.loadActivities()

        verify { observer.onChanged(expectedActivities) }
    }

    @Test
    fun `addActivity adds an activity and refreshes list`() = runTest {
        val duration = 45
        val timestamp = System.currentTimeMillis()
        val newActivity = Activity(duration = duration, timestamp = timestamp)

        coEvery { activityDao.getTodayActivities() } returns listOf(newActivity); Unit

        val observer = mockk<Observer<List<Activity>>>(relaxed = true)
        viewModel.activities.observeForever(observer)

        viewModel.addActivity(duration, timestamp)

        coVerify(exactly = 1) { activityDao.insertActivity(any()) }
        coVerify(exactly = 1) { activityDao.getTodayActivities() }
        verify { observer.onChanged(listOf(newActivity)) }
    }
}