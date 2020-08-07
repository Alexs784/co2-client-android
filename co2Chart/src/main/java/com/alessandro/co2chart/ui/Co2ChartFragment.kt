package com.alessandro.co2chart.ui

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.alessandro.co2chart.R
import com.alessandro.co2chart.di.co2ChartModule
import com.alessandro.co2chart.model.Co2Data
import com.alessandro.co2chart.result.Co2Result
import com.alessandro.co2chart.ui.chart.Co2ChartDateFormatter
import com.alessandro.co2chart.ui.chart.Co2MarkerView
import com.alessandro.co2chart.util.ColorUtil.getColor
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_co2_chart.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class Co2ChartFragment : Fragment() {

    private val co2ChartViewModel by viewModel<Co2ChartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(listOf(co2ChartModule))
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_co2_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setChartStyle()
        observeCo2LiveData()
        co2ChartSwipeToRefreshView.setOnRefreshListener { co2ChartViewModel.loadLatestCo2Data() }
        co2ChartViewModel.loadLatestCo2Data()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_co2_chart, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.darkModeMenuItem).setOnMenuItemClickListener {
            if (isDarkModeEnabled()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            true
        }
    }

    private fun toggleLoading(show: Boolean) {
        co2ChartSwipeToRefreshView.isRefreshing = show
    }

    private fun observeCo2LiveData() {
        co2ChartViewModel.co2LiveData().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Co2Result.Loading -> toggleLoading(it.value)
                is Co2Result.Loaded -> updateChartWithLoadedData(it.co2Data)
                is Co2Result.Error -> showError()
            }
        })
    }

    private fun showError() {
        Snackbar.make(
            co2ChartCoordinatorLayout,
            "Error loading data",
            Snackbar.LENGTH_INDEFINITE
        ).setAction("Dismiss") {}.show()
    }

    private fun setChartStyle() {
        co2LineChartView.setDrawGridBackground(false)
        co2LineChartView.description = Description().also { it.isEnabled = false }
        co2LineChartView.legend.textColor = getColor(R.color.co2_chart_legend_text_color)
        co2LineChartView.marker = Co2MarkerView(requireContext())

        co2LineChartView.axisLeft.setDrawGridLines(false)
        co2LineChartView.axisLeft.textColor = getColor(R.color.co2_chart_axis_text_color)
        co2LineChartView.axisLeft.axisLineColor = getColor(R.color.co2_chart_axis_line_color)
        co2LineChartView.axisRight.setDrawGridLines(false)
        co2LineChartView.axisRight.setDrawAxisLine(false)
        co2LineChartView.axisRight.setDrawLabels(false)

        co2LineChartView.xAxis.position = XAxis.XAxisPosition.BOTTOM
        co2LineChartView.xAxis.setDrawGridLines(false)
        co2LineChartView.xAxis.setDrawAxisLine(true)
        co2LineChartView.xAxis.axisLineColor = Color.GREEN
        co2LineChartView.xAxis.valueFormatter = Co2ChartDateFormatter()
        co2LineChartView.xAxis.textSize = 12f
        co2LineChartView.xAxis.textColor = getColor(R.color.co2_chart_axis_text_color)
        co2LineChartView.xAxis.setLabelCount(4, false)
    }

    private fun updateChartWithLoadedData(co2DataList: List<Co2Data>) {
        val entries = mutableListOf<Entry>()
        val colors = mutableListOf<Int>()
        val xAxisFirstValue = co2DataList.first().date.timeInMillis.toFloat()
        val xAxisLastValue = co2DataList.last().date.timeInMillis.toFloat()

        co2DataList.forEach { co2Data ->
            val entry =
                Entry(co2Data.date.timeInMillis.toFloat(), co2Data.value.toFloat(), co2Data.date)
            colors.add(getColorFor(co2Data))
            entries.add(entry)
        }

        val dataSet = LineDataSet(entries, "Co2 reading")
        dataSet.color = getColor(R.color.co2_chart_line_color)
        dataSet.circleColors = colors
        dataSet.setDrawCircleHole(true)
        dataSet.circleRadius = 3f
        dataSet.valueFormatter = Co2ChartDateFormatter()
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

        val lineData = LineData(dataSet)

        co2LineChartView.xAxis.axisMinimum = xAxisFirstValue
        co2LineChartView.xAxis.axisMaximum = xAxisLastValue
        co2LineChartView.data = lineData
        co2LineChartView.invalidate()
    }

    private fun getColorFor(co2Data: Co2Data): Int {
        return when {
            co2Data.value < 650 -> getColor(R.color.co2_chart_circle_color_co2_low)
            co2Data.value < 850 -> getColor(R.color.co2_chart_circle_color_co2_medium)
            else -> getColor(R.color.co2_chart_circle_color_co2_high)
        }
    }

    private fun isDarkModeEnabled() =
        AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
}