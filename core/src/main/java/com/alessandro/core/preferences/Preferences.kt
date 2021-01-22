package com.alessandro.core.preferences

import kotlinx.coroutines.flow.Flow

interface Preferences {

    suspend fun storeBaseUrl(baseUrl: String)
    suspend fun getBaseUrl(): Flow<String>
}