package com.grok.akm.carousell.di

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    var context: Context? = null
    var appComponent:AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        context = this
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this))
            .utilModule(UtilModule())
            .sortOptionsModule(SortOptionsModule(this))
            .build()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}

