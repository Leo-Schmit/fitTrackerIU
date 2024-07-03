package com.example.fittracker.ui.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fittracker.data.database.AppDatabase
import com.example.fittracker.ui.viewmodels.HomeViewModel
import java.lang.IllegalArgumentException

class HomeViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
