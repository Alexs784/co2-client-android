package com.alessandro.core.repository

import com.alessandro.core.network.entity.Co2Entity

interface RemoteCo2Repository {

    suspend fun getLatestCo2Data(): List<Co2Entity>
}