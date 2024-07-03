package com.example.fittracker.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.fittracker.data.database.AppDatabase
import com.example.fittracker.data.dao.GoalDao
import com.example.fittracker.data.model.Goal
import com.example.fittracker.ui.viewmodels.SettingsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.After
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class SettingsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SettingsViewModel
    private val goalDao = mockk<GoalDao>(relaxed = true)
    private val database = mockk<AppDatabase>()
    private val goalObserver = mockk<Observer<Goal?>>(relaxed = true)
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { database.goalDao() } returns goalDao
        viewModel = SettingsViewModel(database)
        viewModel.goal.observeForever(goalObserver)
    }

    @After
    fun tearDown() {
        viewModel.goal.removeObserver(goalObserver)
        Dispatchers.resetMain()
    }

    @Test
    fun `load current goal should fetch and post goal`() = runTest {
        val expectedGoal = Goal(1, 150)
        coEvery { goalDao.getGoal() } returns expectedGoal

        viewModel.loadCurrentGoal()

        verify { goalObserver.onChanged(expectedGoal) }
    }

    @Test
    fun `update goal should update and refresh goal`() = runTest {
        val newGoalValue = 200
        val updatedGoal = Goal(1, newGoalValue)
        coEvery { goalDao.insertOrUpdateGoal(any()) } returns 1L
        coEvery { goalDao.getGoal() } returns updatedGoal

        viewModel.updateGoal(newGoalValue)

        verify { goalObserver.onChanged(updatedGoal) }
    }
}