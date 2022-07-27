package com.example.dictionaryapp.data.repository

import com.example.dictionaryapp.domain.model.WordInfo
import com.example.dictionaryapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWordInfoRepository : WordInfoRepository {

    var getWordInfosFlow = flow<Resource<List<WordInfo>>> {}
    override fun getWordInfos(word: String): Flow<Resource<List<WordInfo>>> {
        return getWordInfosFlow
    }
}
