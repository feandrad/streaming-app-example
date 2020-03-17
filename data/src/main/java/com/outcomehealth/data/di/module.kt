package com.outcomehealth.data.di

import com.outcomehealth.data.VideoRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module(override = true) {
    single { VideoRepository(androidContext()) }
}


