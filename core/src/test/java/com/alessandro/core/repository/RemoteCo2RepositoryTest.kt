package com.alessandro.core.repository

import com.alessandro.core.network.api.Co2API
import com.alessandro.core.network.entity.Co2Entity
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RemoteCo2RepositoryTest() {

    @Mock
    private lateinit var co2Api: Co2API

    private lateinit var remoteCo2Repository: RemoteCo2Repository

    @Before
    fun setUp() {
        remoteCo2Repository = RemoteCo2RepositoryImpl(co2Api)
    }

    @Test
    fun shouldReturnLatestCo2DataOrdered() = runBlocking {
        val latestCo2Data = listOf(
            Co2Entity(0, 100, ""),
            Co2Entity(1, 200, "")
        )
        given(co2Api.getLatestCo2Data()).willReturn(latestCo2Data)

        val result = remoteCo2Repository.getLatestCo2Data()

        assertEquals(latestCo2Data.reversed(), result)
    }
}