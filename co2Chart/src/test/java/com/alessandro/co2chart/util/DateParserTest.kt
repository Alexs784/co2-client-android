package com.alessandro.co2chart.util

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class DateParserTest {

    private lateinit var dateParser: DateParser

    @Before
    fun setUp() {
        dateParser = DateParser()
    }

    @Test
    fun shouldParseDateStringToCalendar() {
        val date = "2020-06-06T14:40:02.000Z"
        val format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val expectedCalendar = Calendar.getInstance().also {
            it.set(Calendar.DAY_OF_MONTH, 6)
            it.set(Calendar.MONTH, 5)
            it.set(Calendar.YEAR, 2020)
            it.set(Calendar.HOUR_OF_DAY, 14)
            it.set(Calendar.MINUTE, 40)
            it.set(Calendar.SECOND, 2)
            it.set(Calendar.MILLISECOND, 0)
        }

        val result = dateParser.parseToCalendar(date, format)

        assertEquals(result, expectedCalendar)
    }
}