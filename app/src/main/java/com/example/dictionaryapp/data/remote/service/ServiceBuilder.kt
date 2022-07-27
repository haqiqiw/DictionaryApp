package com.example.dictionaryapp.data.remote.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private const val BASE_URL = "https://api.dictionaryapi.dev"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    val dictionaryService: DictionaryService by lazy {
        retrofit.create(DictionaryService::class.java)
    }
}
