package com.example.digibuddy.features.dashboard

import android.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digibuddy.di.ViewModelFactory
import com.example.digibuddy.features.recommendations.RecommendationEngine
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun DashboardScreen() {
    val context = LocalContext.current
    val dashboardViewModel: DashboardViewModel = viewModel(factory = ViewModelFactory(context))
    val history by dashboardViewModel.history.collectAsState()
    val recommendationEngine = RecommendationEngine(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Dashboard", style = MaterialTheme.typography.headlineMedium)

        val latestHistoryData = history.take(5).reversed()

        // Stress Level Chart
        Text("Stress Level History", style = MaterialTheme.typography.titleMedium)
        val stressEntries = latestHistoryData.mapIndexed { index, item ->
            val stressValue = when (item.stressLevel) {
                "Low" -> 0f
                "Medium" -> 1f
                "High" -> 2f
                else -> 0f
            }
            Entry(index.toFloat(), stressValue)
        }
        val stressDataSet = LineDataSet(stressEntries, "Stress Level").apply {
            color = Color.RED
            valueTextColor = Color.BLACK
            setDrawValues(false)
            lineWidth = 2f
            setCircleColor(Color.RED)
            circleRadius = 4f
            setDrawCircleHole(false)
        }
        AndroidView(
            modifier = Modifier.fillMaxWidth().height(200.dp),
            factory = { ctx ->
                LineChart(ctx).apply {
                    data = LineData(stressDataSet)
                    description.isEnabled = false
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    axisRight.isEnabled = false
                    legend.isEnabled = false
                    setVisibleXRangeMaximum(5f)
                    setDrawGridBackground(false)
                    invalidate()
                }
            },
            update = { chart ->
                chart.data = LineData(stressDataSet)
                chart.notifyDataSetChanged()
                chart.invalidate()
            }
        )

        // Device Hours Chart
        Text("Device Hours History", style = MaterialTheme.typography.titleMedium)
        val deviceHoursEntries = latestHistoryData.mapIndexed { index, item ->
            Entry(index.toFloat(), item.device_hours_per_day)
        }
        val deviceHoursDataSet = LineDataSet(deviceHoursEntries, "Device Hours per Day").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
            setDrawValues(false)
            lineWidth = 2f
            setCircleColor(Color.BLUE)
            circleRadius = 4f
            setDrawCircleHole(false)
        }
        AndroidView(
            modifier = Modifier.fillMaxWidth().height(200.dp),
            factory = { ctx ->
                LineChart(ctx).apply {
                    data = LineData(deviceHoursDataSet)
                    description.isEnabled = false
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    axisRight.isEnabled = false
                    legend.isEnabled = false
                    setVisibleXRangeMaximum(5f)
                    setDrawGridBackground(false)
                    invalidate()
                }
            },
            update = { chart ->
                chart.data = LineData(deviceHoursDataSet)
                chart.notifyDataSetChanged()
                chart.invalidate()
            }
        )

        val latestHistory = history.firstOrNull()
        if (latestHistory != null) {
            Text("Latest Stress Level: ${latestHistory.stressLevel}", style = MaterialTheme.typography.titleMedium)
            Text(recommendationEngine.getRecommendation(latestHistory))
        }

        Text(
            text = "This is a statistical estimate, not a medical diagnosis.",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
