package com.example.fittracker.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fittracker.data.database.AppDatabase
import com.example.fittracker.data.model.MonthlyActivity
import com.example.fittracker.databinding.FragmentProgressBinding
import com.example.fittracker.ui.viewmodels.ProgressViewModel
import com.example.fittracker.ui.viewmodels.factories.ProgressViewModelFactory
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Locale

class ProgressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProgressViewModel by viewModels {
        ProgressViewModelFactory(AppDatabase.getDatabase(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadMonthlyActivitiesAndGoal()
        viewModel.monthlyActivities.observe(viewLifecycleOwner) { activities ->
            if (activities.isNotEmpty()) {
                setupChart(activities, viewModel.currentGoal.value ?: 150f)
            }
        }
    }

    private fun setupChart(monthlyActivities: List<MonthlyActivity>, currentGoal: Float) {
        val entries = monthlyActivities.mapIndexed { index, activity ->
            Entry(index.toFloat() + 1, activity.totalDuration.toFloat())
        }
        val dataSet = LineDataSet(entries, "Activity (minutes per week)").apply {
            color = ColorTemplate.getHoloBlue()
            valueTextColor = Color.BLACK
            valueTextSize = 18f
            lineWidth = 2.5f
            circleRadius = 8f
            circleHoleColor = Color.WHITE
            circleColors = listOf(ColorTemplate.getHoloBlue())
            setDrawCircles(true)
            setDrawCircleHole(true)
            setDrawFilled(true)
            fillColor = ColorTemplate.getHoloBlue()
            mode = LineDataSet.Mode.LINEAR
            setDrawHighlightIndicators(false)
            setDrawValues(true)
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString()
                }
            }
        }
        val limitLine = LimitLine(currentGoal, "Goal").apply {
            lineWidth = 2f
            enableDashedLine(10f, 10f, 0f)
            lineColor = Color.GRAY
            textColor = Color.DKGRAY
            textSize = 12f
            labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        }
        val maxActivity = if (entries.isNotEmpty()) entries.maxOf { it.y } else 0f
        val chartMax = maxOf(currentGoal, maxActivity) * 1.1f
        binding.chart.apply {
            data = LineData(dataSet)
            description.isEnabled = false
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                valueFormatter =
                    IndexAxisValueFormatter(monthlyActivities.map { formatDateLabel(it.month) })
                granularity = 1f
                isGranularityEnabled = true
            }
            axisLeft.apply {
                removeAllLimitLines()
                addLimitLine(limitLine)
                setDrawGridLines(true)
                axisMinimum = 0f
                axisMaximum = chartMax
                setDrawLabels(true)
            }
            axisRight.isEnabled = false
            setTouchEnabled(true)
            setPinchZoom(true)
            setDrawGridBackground(false)
            animateX(500)
            extraTopOffset = 30f
            invalidate()
        }
    }

    private fun formatDateLabel(dateStr: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        val outputFormat = SimpleDateFormat(
            "MMM",
            Locale.getDefault()
        ) // Использование "MMM" для сокращённого названия месяца
        val date = inputFormat.parse(dateStr) ?: return dateStr
        return outputFormat.format(date)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
