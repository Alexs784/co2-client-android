package com.alessandro.core.preferences

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import com.alessandro.core.R

class DataStore(private val context: Context) {

    fun getDefaultDataStore(): DataStore<Preferences> {
        return context.createDataStore(context.getString(R.string.data_store_default))
    }
}