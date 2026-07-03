package com.kuit.youthroulette.data

object SessionManager {

    var accessToken: String? = null
        private set

    var loginId: String? = null
        private set

    var nickname: String? = null
        private set

    fun updateAuth(accessToken: String?, loginId: String?, nickname: String?) {
        if (accessToken != null) this.accessToken = accessToken
        if (loginId != null) this.loginId = loginId
        if (nickname != null) this.nickname = nickname
    }

    fun updateNickname(nickname: String) {
        this.nickname = nickname
    }

    fun clear() {
        accessToken = null
        loginId = null
        nickname = null
    }
}
