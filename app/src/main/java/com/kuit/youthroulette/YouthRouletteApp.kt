package com.kuit.youthroulette

import android.app.Application

class YouthRouletteApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        lateinit var appContext: Application
            private set
    }
}
