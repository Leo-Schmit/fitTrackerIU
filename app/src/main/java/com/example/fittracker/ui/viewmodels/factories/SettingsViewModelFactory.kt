package com.example.fittracker.ui.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fittracker.data.database.AppDatabase
import com.example.fittracker.ui.viewmodels.SettingsViewModel
import java.lang.IllegalArgumentException

class SettingsViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
