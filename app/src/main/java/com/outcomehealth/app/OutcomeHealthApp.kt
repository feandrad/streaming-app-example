package com.outcomehealth.app

import android.app.Application
import com.outcomehealth.app.di.coreModule
import com.outcomehealth.data.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class OutcomeHealthApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDependencyInjection()
    }

    private fun initDependencyInjection() {
        startKoin {
            androidContext(this@OutcomeHealthApp)
            modules(listOf(coreModule, dataModule))
        }
    }
}