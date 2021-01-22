package com.alessandro.core.preferences

import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesImpl(private val dataStore: DataStore) : Preferences {

    companion object {
        val DATA_STORE_BASE_URL = preferencesKey<String>("DATA_STORE_BASE_URL")
    }

    override suspend fun storeBaseUrl(baseUrl: String) {
        dataStore.getDefaultDataStore().edit { settings ->
            settings[DATA_STORE_BASE_URL] = baseUrl
        }
    }

    override suspend fun getBaseUrl(): Flow<String> {
        return dataStore.getDefaultDataStore().data.map { settings ->
            settings[DATA_STORE_BASE_URL] ?: ""
        }
    }
}