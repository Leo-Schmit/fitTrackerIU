package com.example.fittracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittracker.data.database.AppDatabase
import com.example.fittracker.data.model.Goal
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData

class SettingsViewModel(private val database: AppDatabase) : ViewModel() {

    private val _goal = MutableLiveData<Goal?>()
    val goal: LiveData<Goal?> = _goal
    var userInitiatedChange = false

    fun loadCurrentGoal() {
        viewModelScope.launch {
            _goal.postValue(database.goalDao().getGoal())
        }
    }

    fun updateGoal(newGoal: Int) {
        viewModelScope.launch {
            database.goalDao().insertOrUpdateGoal(Goal(goal = newGoal))
            loadCurrentGoal()
        }
    }
}
