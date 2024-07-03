package com.example.fittracker.ui.fragments

import android.content.Context
import com.example.fittracker.ui.viewmodels.factories.SettingsViewModelFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fittracker.data.database.AppDatabase
import com.example.fittracker.databinding.FragmentSettingsBinding
import com.example.fittracker.ui.viewmodels.SettingsViewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory(AppDatabase.getDatabase(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.save.setOnClickListener {
            val goalStr = binding.goal.text.toString()
            val goal = goalStr.toIntOrNull()
            if (goal != null) {
                viewModel.updateGoal(goal)
                viewModel.userInitiatedChange = true
            } else {
                Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.goal.observe(viewLifecycleOwner) { goal ->
            goal?.let {
                binding.goal.setText(it.goal.toString())
                if (viewModel.userInitiatedChange) {
                    Toast.makeText(
                        context,
                        "Goal updated: ${it.goal} minutes per week",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.userInitiatedChange = false
                    hideKeyboard()
                }
            }
        }

        viewModel.loadCurrentGoal()

        return binding.root
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
