package com.kuit.youthroulette

import android.app.Application
import com.kuit.youthroulette.data.SessionManager

class YouthRouletteApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SessionManager.init(this)
    }
}
