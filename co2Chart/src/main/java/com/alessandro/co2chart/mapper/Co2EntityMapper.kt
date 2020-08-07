package com.alessandro.co2chart.mapper

import com.alessandro.co2chart.model.Co2Data
import com.alessandro.co2chart.util.DateParser
import com.alessandro.core.network.entity.Co2Entity

class Co2EntityMapper(private val dateParser: DateParser) {

    fun transformEntityToData(co2Entity: Co2Entity): Co2Data {
        val co2Value = co2Entity.value
        val co2Date = dateParser.parseToCalendar(co2Entity.createdAt, Co2Entity.DATE_FORMAT)
        return Co2Data(co2Value, co2Date)
    }

    fun transformEntityToData(co2Entities: List<Co2Entity>): List<Co2Data> {
        return co2Entities.map { co2Entity -> transformEntityToData(co2Entity) }
    }
}