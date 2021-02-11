package com.e.brastlewark

import android.app.Application
import com.e.brastlewark.di.repositoriesModule
import com.e.brastlewark.di.useCasesModule
import com.e.brastlewark.di.viewModelModule
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GnomesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this)

        startKoin {
            androidContext(this@GnomesApplication)
            modules(listOf(repositoriesModule, viewModelModule, useCasesModule))
        }
    }
}