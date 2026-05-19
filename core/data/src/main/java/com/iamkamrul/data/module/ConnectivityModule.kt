package com.iamkamrul.data.module

import com.iamkamrul.data.client.ConnectivityObserverImpl
import com.iamkamrul.domain.connectivity.ConnectivityObserver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConnectivityModule {

    @Binds
    @Singleton
    abstract fun bindConnectivityObserver(impl: ConnectivityObserverImpl): ConnectivityObserver
}