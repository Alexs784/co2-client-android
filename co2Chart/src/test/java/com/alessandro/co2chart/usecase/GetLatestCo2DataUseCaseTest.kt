package com.alessandro.co2chart.usecase

import com.alessandro.co2chart.mapper.Co2EntityMapper
import com.alessandro.co2chart.model.Co2Data
import com.alessandro.co2chart.result.Co2Result
import com.alessandro.core.network.entity.Co2Entity
import com.alessandro.core.repository.RemoteCo2Repository
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class GetLatestCo2DataUseCaseTest {

    @Mock
    private lateinit var co2EntityMapper: Co2EntityMapper

    @Mock
    private lateinit var remoteCo2Repository: RemoteCo2Repository

    private lateinit var getLatestCo2DataUseCase: GetLatestCo2DataUseCase

    private val remoteRepositoryResult = listOf(Co2Entity(0, 200, ""))

    @Before
    fun setUp() {
        getLatestCo2DataUseCase = GetLatestCo2DataUseCase(co2EntityMapper, remoteCo2Repository)
        runBlocking {
            given(remoteCo2Repository.getLatestCo2Data()).willReturn(remoteRepositoryResult)
        }
    }

    @Test
    fun shouldReturnCo2DataLoadedIfDataIsFetchedCorrectly() = runBlocking {
        val calendar = Calendar.getInstance()

        val mapperResult = listOf(Co2Data(200, calendar))

        given(co2EntityMapper.transformEntityToData(remoteRepositoryResult)).willReturn(mapperResult)

        val result = getLatestCo2DataUseCase.execute()

        assertEquals(result, Co2Result.Loaded(mapperResult))
    }

    @Test
    fun shouldReturnErrorIfFetchingDataFromRemoteFails() = runBlocking {
        given(remoteCo2Repository.getLatestCo2Data()).willThrow(IllegalStateException())

        val result = getLatestCo2DataUseCase.execute()

        assertEquals(result, Co2Result.Error)
    }

    @Test
    fun shouldReturnErrorIfTransformingDataFails() = runBlocking {
        given(co2EntityMapper.transformEntityToData(remoteRepositoryResult))
            .willThrow(IllegalStateException())

        val result = getLatestCo2DataUseCase.execute()

        assertEquals(result, Co2Result.Error)
    }
}