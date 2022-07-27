package com.example.dictionaryapp.data.repository

import com.example.dictionaryapp.domain.model.WordInfo
import com.example.dictionaryapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {

    fun getWordInfos(word: String): Flow<Resource<List<WordInfo>>>
}
