package com.example.fittracker.ui.fragments

import com.example.fittracker.ui.viewmodels.factories.HomeViewModelFactory
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fittracker.data.database.AppDatabase
import com.example.fittracker.databinding.FragmentHomeBinding
import com.example.fittracker.ui.adapters.ActivityAdapter
import com.example.fittracker.ui.viewmodels.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(AppDatabase.getDatabase(requireContext()))
    }
    private lateinit var activityAdapter: ActivityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        activityAdapter = ActivityAdapter(emptyList())

        binding.rvActivities.layoutManager = LinearLayoutManager(context)
        binding.rvActivities.adapter = activityAdapter

        binding.btnAdd.setOnClickListener {
            addActivity()
        }

        viewModel.activities.observe(viewLifecycleOwner) { activities ->
            activityAdapter.updateActivities(activities)
        }

        viewModel.loadActivities()

        return binding.root
    }

    private fun addActivity() {
        val durationStr = binding.etDuration.text.toString()
        val duration = durationStr.toIntOrNull()
        if (duration == null || duration <= 0) {
            Toast.makeText(context, "Please enter a valid number of minutes.", Toast.LENGTH_SHORT)
                .show()
            return
        }

        viewModel.addActivity(duration, System.currentTimeMillis())
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            val imm =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
