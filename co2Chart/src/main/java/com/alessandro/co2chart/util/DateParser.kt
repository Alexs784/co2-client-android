package com.alessandro.co2chart.util

import java.text.SimpleDateFormat
import java.util.*

class DateParser {

    fun parseToCalendar(date: String, dateFormat: String): Calendar {
        val date = SimpleDateFormat(dateFormat, Locale.getDefault()).parse(date)
        return Calendar.getInstance().also { it.time = date }
    }
}