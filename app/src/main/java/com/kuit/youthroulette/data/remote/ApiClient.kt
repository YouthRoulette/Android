package com.kuit.youthroulette.data.remote
//Retrofit 객체를 만드는 파일
import com.kuit.youthroulette.data.local.TokenStore
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://strung-frostbite-poster.ngrok-free.dev/"

    // 로그인 후 저장된 accessToken을 모든 요청 헤더에 붙여줌
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val token = TokenStore.accessToken
            val request = if (token != null) {
                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            } else {
                chain.request()
            }
            chain.proceed(request)
        }
        .build()

    val api: YouthRouletteApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YouthRouletteApi::class.java)
    }
}