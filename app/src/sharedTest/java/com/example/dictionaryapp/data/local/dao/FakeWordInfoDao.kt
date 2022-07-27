package com.example.dictionaryapp.data.local.dao

import com.example.dictionaryapp.data.local.entity.WordInfoEntity

class FakeWordInfoDao : WordInfoDao {

    override suspend fun insertWordInfos(wordInfos: List<WordInfoEntity>) = Unit

    override suspend fun deleteWordInfos(words: List<String>) = Unit

    var getWordInfosResult = listOf<WordInfoEntity>()
    override suspend fun getWordInfos(word: String): List<WordInfoEntity> {
        return getWordInfosResult
    }
}
