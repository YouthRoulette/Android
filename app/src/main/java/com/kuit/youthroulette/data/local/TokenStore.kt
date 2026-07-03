package com.kuit.youthroulette.data.local
//로그인 accessToken을 기기에 저장/조회하는 파일
import android.content.Context
import androidx.core.content.edit
import com.kuit.youthroulette.YouthRouletteApp

object TokenStore {

    private const val PREFS_NAME = "auth_prefs"
    private const val KEY_ACCESS_TOKEN = "access_token"

    private val prefs by lazy {
        YouthRouletteApp.appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var accessToken: String?
        get() = prefs.getString(KEY_ACCESS_TOKEN, null)
        set(value) = prefs.edit { putString(KEY_ACCESS_TOKEN, value) }

    fun clear() {
        prefs.edit { remove(KEY_ACCESS_TOKEN) }
    }
}
