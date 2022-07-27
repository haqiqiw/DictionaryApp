package com.example.dictionaryapp.data.remote.service

import com.example.dictionaryapp.data.remote.dto.WordInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryService {

    @GET("/api/v2/entries/en/{word}")
    suspend fun getWordInfos(
        @Path("word") word: String
    ): List<WordInfoDto>
}
