package com.hackatron52.androidacademyhackathon.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

private const val KEY_API_KEY = "key"

class MapsApiKeyInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter(KEY_API_KEY, apiKey).build()
        request = request.newBuilder().url(url).build()

        return chain.proceed(request)
    }
}