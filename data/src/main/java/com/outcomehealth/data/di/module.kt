package com.outcomehealth.data.di

import com.outcomehealth.data.BuildConfig
import com.outcomehealth.data.VideoRepository
import com.outcomehealth.data.api.VideoApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module(override = true) {
    single { createOkHttpClient() }
    single { provideApi(get()) }
    single { provideRetrofit(get()) }

    single { VideoRepository(androidContext(), get()) }
}


fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.VIDEO_MANIFEST)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun provideApi(retrofit: Retrofit): VideoApi = retrofit.create(VideoApi::class.java)