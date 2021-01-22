package com.alessandro.core.di

import com.alessandro.core.preferences.DataStore
import com.alessandro.core.preferences.Preferences
import com.alessandro.core.preferences.PreferencesImpl
import org.koin.dsl.module

val module = module {
    single { DataStore(get()) }
    single<Preferences> { PreferencesImpl(get()) }
}