package com.example.actividad2

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://hawkconnect.azurewebsites.net/campus.aspx/" // 👈 pon aquí tu URL base

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

object RetrofitClient2 {
    private const val BASE_URL = "https://hawkconnect.azurewebsites.net/users.aspx/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

object RetrofitClient3 {
    private const val BASE_URL = "https://hawkconnect.azurewebsites.net/posts.aspx/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}