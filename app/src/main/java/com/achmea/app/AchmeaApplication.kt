package com.achmea.app

import android.app.Application
import com.achmea.di.appModule
import com.achmea.di.useCaseModule
import com.achmea.di.viewModelModule
import org.koin.core.context.GlobalContext.startKoin

class AchmeaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin { modules(appModule, viewModelModule, useCaseModule) }
    }
}