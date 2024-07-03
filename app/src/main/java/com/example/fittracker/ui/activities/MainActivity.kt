package com.example.fittracker.activities;

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fittracker.R
import com.example.fittracker.databinding.ActivityMainBinding
import com.example.fittracker.ui.fragments.HomeFragment
import com.example.fittracker.ui.fragments.ProgressFragment
import com.example.fittracker.ui.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {

    // Lateinit allows the binding to be initialized later.
    private lateinit var binding: ActivityMainBinding

    // Inflates the main layout and sets up navigation, ensuring all UI components are ready before interaction.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Inflates the layout and initializes binding.
        setContentView(binding.root) // Sets the view for the activity.
        setupBottomNavigation() // Sets up navigation items and listeners.
    }

    // Configures the bottom navigation view to handle item selections and manage fragments.
    private fun setupBottomNavigation() {
        // Listener for item selection in the navigation bar.
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.settings -> {
                    replaceFragment(SettingsFragment())
                    true
                }
                R.id.progress -> {
                    replaceFragment(ProgressFragment())
                    true
                }
                else -> false // Fallback behavior: ignores any unrecognized menu item selections.
            }
        }

        // Pre-selects the home tab to ensure the main view is visible immediately on launch.
        binding.bottomNavigationView.selectedItemId = R.id.home
    }

    // Replaces the currently displayed fragment with a new one in the designated fragment container.
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment) // Replaces any existing fragment in the container with a new one.
            .commit() // Commits the transaction.
    }
}
