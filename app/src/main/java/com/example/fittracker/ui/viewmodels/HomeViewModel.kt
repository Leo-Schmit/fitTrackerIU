package com.example.fittracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittracker.data.database.AppDatabase
import com.example.fittracker.data.model.Activity
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData

class HomeViewModel(private val database: AppDatabase) : ViewModel() {
    private val _activities = MutableLiveData<List<Activity>>()
    val activities: LiveData<List<Activity>> = _activities

    fun loadActivities() {
        viewModelScope.launch {
            val activitiesList = database.activityDao().getTodayActivities()
            _activities.postValue(activitiesList)
        }
    }

    fun addActivity(duration: Int, timestamp: Long) {
        viewModelScope.launch {
            database.activityDao()
                .insertActivity(Activity(duration = duration, timestamp = timestamp))
            loadActivities()
        }
    }
}
