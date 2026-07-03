package com.kuit.youthroulette.data.remote

import com.kuit.youthroulette.data.SessionManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URI
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val SERVER_URL = "https://strung-frostbite-poster.ngrok-free.dev/api/auth/login"

    private val BASE_URL: String = normalizeToRoot(SERVER_URL)

    private fun normalizeToRoot(url: String): String {
        return runCatching {
            val uri = URI(url.trim())
            val scheme = uri.scheme ?: return@runCatching ensureTrailingSlash(url.trim())
            val host = uri.host ?: return@runCatching ensureTrailingSlash(url.trim())
            val port = if (uri.port == -1) "" else ":${uri.port}"
            "$scheme://$host$port/"
        }.getOrElse { ensureTrailingSlash(url.trim()) }
    }

    private fun ensureTrailingSlash(url: String): String =
        if (url.endsWith("/")) url else "$url/"

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                    .addHeader("ngrok-skip-browser-warning", "true")
                val token = SessionManager.accessToken
                if (!token.isNullOrBlank()) {
                    builder.addHeader("Authorization", "Bearer $token")
                }
                chain.proceed(builder.build())
            }
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    val api: YouthRouletteApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YouthRouletteApi::class.java)
    }
}
