package br.com.lucas.pomotimer.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            defaultModule()
            androidContext(this@AppApplication)
        }
    }

}