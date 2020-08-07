package com.alessandro.co2chart.result

import com.alessandro.co2chart.model.Co2Data

sealed class Co2Result {

    data class Loading(val value: Boolean) : Co2Result()

    data class Loaded(val co2Data: List<Co2Data>) : Co2Result()

    object Error : Co2Result()
}