package com.alessandro.core.di

import com.alessandro.core.repository.RemoteCo2Repository
import com.alessandro.core.repository.RemoteCo2RepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<RemoteCo2Repository> { RemoteCo2RepositoryImpl(get()) }
}