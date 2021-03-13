package com.hackatron52.androidacademyhackathon.di

import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreNetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        context: Context,
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(getBaseUrl(context.resources))
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): Converter.Factory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(CONNECTION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLogger = object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d(OkHttpClient::class.java.name, message)
            }
        }
        val loggingInterceptor = HttpLoggingInterceptor(httpLogger)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private fun getBaseUrl(resources: Resources): String =
        ""

    companion object {
        private const val READ_TIMEOUT_IN_SECONDS = 20L
        private const val CONNECTION_TIMEOUT_IN_SECONDS = 20L
    }
}