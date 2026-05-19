package com.iamkamrul.domain.connectivity


import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun isConnected(): Boolean
    fun observe(): Flow<Boolean>
}

