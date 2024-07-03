package com.example.fittracker.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittracker.data.database.AppDatabase
import com.example.fittracker.data.model.MonthlyActivity
import kotlinx.coroutines.launch

class ProgressViewModel(private val database: AppDatabase) : ViewModel() {
    private val _monthlyActivities = MutableLiveData<List<MonthlyActivity>>()
    val monthlyActivities: LiveData<List<MonthlyActivity>> = _monthlyActivities

    private val _currentGoal = MutableLiveData<Float>()
    val currentGoal: LiveData<Float> = _currentGoal

    fun loadMonthlyActivitiesAndGoal() {
        viewModelScope.launch {
            _currentGoal.postValue(database.goalDao().getGoal()?.goal?.toFloat() ?: 150f)
            _monthlyActivities.postValue(database.activityDao().getMonthlyActivities())
        }
    }
}
