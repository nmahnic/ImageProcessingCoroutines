package com.nicomahnic.imageprocessingcoroutines

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient  {

    val service : Api
        get() {
            val logging = provideOkHttpClientLogging()
            val okHttpClient = provideOkHttpClient(logging)
            val retrofit = provideRetrofit(okHttpClient)
            return provideBackendService(retrofit)
        }

    private fun provideBackendService(retrofit: Retrofit) : Api {
        return retrofit.create(Api::class.java)
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .callTimeout(11, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    private fun provideOkHttpClientLogging(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}