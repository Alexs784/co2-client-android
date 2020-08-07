package com.alessandro.co2chart.ui.chart

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.util.*

class Co2ChartDateFormatter : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = value.toLong()
        return getFormattedDate(calendar)
    }

    private fun getFormattedDate(calendar: Calendar): String {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())
        val hour = calendar.get(Calendar.HOUR)
        val hourAppend = if (calendar.get(Calendar.AM_PM) == 0) "AM" else "PM"
        return "$day $month - $hour$hourAppend"
    }
}