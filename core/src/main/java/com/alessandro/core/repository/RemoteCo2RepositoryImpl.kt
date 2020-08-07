package com.alessandro.core.repository

import com.alessandro.core.network.api.Co2API
import com.alessandro.core.network.entity.Co2Entity

class RemoteCo2RepositoryImpl(private val co2API: Co2API) : RemoteCo2Repository {

    override suspend fun getLatestCo2Data(): List<Co2Entity> {
        return co2API.getLatestCo2Data().reversed()
    }
}