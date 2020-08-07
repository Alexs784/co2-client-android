package com.alessandro.core.network.api

import com.alessandro.core.network.entity.Co2Entity
import retrofit2.http.GET

interface Co2API {

    @GET("co2_data")
    suspend fun getLatestCo2Data(): List<Co2Entity>
}