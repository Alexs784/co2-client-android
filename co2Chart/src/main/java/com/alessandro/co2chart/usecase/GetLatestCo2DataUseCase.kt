package com.alessandro.co2chart.usecase

import com.alessandro.co2chart.mapper.Co2EntityMapper
import com.alessandro.co2chart.result.Co2Result
import com.alessandro.core.repository.RemoteCo2Repository

class GetLatestCo2DataUseCase(
    private val co2EntityMapper: Co2EntityMapper,
    private val remoteCo2Repository: RemoteCo2Repository
) {

    suspend fun execute(): Co2Result {
        return try {
            val co2RemoteData = remoteCo2Repository.getLatestCo2Data()
            val co2Data = co2EntityMapper.transformEntityToData(co2RemoteData)
            Co2Result.Loaded(co2Data)
        } catch (exception: Exception) {
            Co2Result.Error
        }
    }
}