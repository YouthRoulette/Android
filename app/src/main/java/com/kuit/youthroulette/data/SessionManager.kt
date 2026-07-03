package com.kuit.youthroulette.data

import android.content.Context
import android.content.SharedPreferences

object SessionManager {

    private const val PREFS_NAME = "youth_roulette_session"
    private const val KEY_ACCESS_TOKEN = "access_token"
    private const val KEY_LOGIN_ID = "login_id"
    private const val KEY_NICKNAME = "nickname"
    private const val KEY_LAST_ROUTE = "last_route"
    private const val KEY_HAS_LAUNCHED = "has_launched_before"

    private lateinit var prefs: SharedPreferences

    var accessToken: String? = null
        private set

    var loginId: String? = null
        private set

    var nickname: String? = null
        private set

    var lastRoute: String? = null
        private set

    var hasLaunchedBefore: Boolean = false
        private set

    fun init(context: Context) {
        prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        accessToken = prefs.getString(KEY_ACCESS_TOKEN, null)
        loginId = prefs.getString(KEY_LOGIN_ID, null)
        nickname = prefs.getString(KEY_NICKNAME, null)
        lastRoute = prefs.getString(KEY_LAST_ROUTE, null)
        hasLaunchedBefore = prefs.getBoolean(KEY_HAS_LAUNCHED, false)
    }

    fun isLoggedIn(): Boolean = !accessToken.isNullOrBlank()

    fun markLaunched() {
        if (hasLaunchedBefore) return
        hasLaunchedBefore = true
        prefs.edit().putBoolean(KEY_HAS_LAUNCHED, true).commit()
    }

    fun updateAuth(accessToken: String?, loginId: String?, nickname: String?) {
        if (accessToken != null) this.accessToken = accessToken
        if (loginId != null) this.loginId = loginId
        if (nickname != null) this.nickname = nickname
        persistAuth()
    }

    fun updateNickname(nickname: String) {
        this.nickname = nickname
        persistAuth()
    }

    fun saveLastRoute(route: String) {
        lastRoute = route
        prefs.edit().putString(KEY_LAST_ROUTE, route).apply()
    }

    fun clear() {
        accessToken = null
        loginId = null
        nickname = null
        lastRoute = null
        hasLaunchedBefore = false
        prefs.edit().clear().commit()
    }

    private fun persistAuth() {
        prefs.edit()
            .putString(KEY_ACCESS_TOKEN, accessToken)
            .putString(KEY_LOGIN_ID, loginId)
            .putString(KEY_NICKNAME, nickname)
            .commit()
    }
}
