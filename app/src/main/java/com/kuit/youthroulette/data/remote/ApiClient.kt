package com.kuit.youthroulette.data.remote
//Retrofit 객체를 만드는 파일
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object ApiClient {

    private const val BASE_URL = "http://서버주소/"//todo수정필요

    val api: YouthRouletteApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YouthRouletteApi::class.java)
    }
}