package com.alessandro.co2chart.mapper

import com.alessandro.co2chart.util.DateParser
import com.alessandro.core.network.entity.Co2Entity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class Co2EntityMapperTest {

    private lateinit var dateParser: DateParser

    private lateinit var co2EntityMapper: Co2EntityMapper

    @Before
    fun setUp() {
        dateParser = DateParser()
        co2EntityMapper = Co2EntityMapper(dateParser)
    }

    @Test
    fun shouldTransformEntityToDataModel() {
        val irrelevantId = 0
        val expectedCo2Value = 100
        val date = "2020-06-06T14:40:02.000Z"
        val expectedCalendar = Calendar.getInstance().also {
            it.set(Calendar.DAY_OF_MONTH, 6)
            it.set(Calendar.MONTH, 5)
            it.set(Calendar.YEAR, 2020)
            it.set(Calendar.HOUR_OF_DAY, 14)
            it.set(Calendar.MINUTE, 40)
            it.set(Calendar.SECOND, 2)
            it.set(Calendar.MILLISECOND, 0)
        }

        val co2Entity = Co2Entity(irrelevantId, expectedCo2Value, date)

        val result = co2EntityMapper.transformEntityToData(co2Entity)

        assertEquals(expectedCo2Value, result.value)
        assertEquals(expectedCalendar, result.date)
    }
}