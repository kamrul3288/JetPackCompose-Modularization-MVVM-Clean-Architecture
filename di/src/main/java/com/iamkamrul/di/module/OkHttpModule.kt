package com.iamkamrul.di.module


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHttpModule {

    @Provides
    @Singleton
    fun provideLoggerInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message -> Timber.e(message) }
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.HEADERS }
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggerInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val timeOut = 30
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .readTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .writeTimeout(timeOut.toLong(), TimeUnit.SECONDS)

        httpClient.addInterceptor(loggerInterceptor)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("Accept", "application/json")
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        return httpClient.build()
    }
}