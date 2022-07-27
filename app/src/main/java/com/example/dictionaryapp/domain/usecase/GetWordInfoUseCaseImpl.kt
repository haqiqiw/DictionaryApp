package com.example.dictionaryapp.domain.usecase

import com.example.dictionaryapp.data.repository.WordInfoRepository
import com.example.dictionaryapp.domain.model.WordInfo
import com.example.dictionaryapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWordInfoUseCaseImpl(
    private val wordInfoRepository: WordInfoRepository
) : GetWordInfoUseCase {
    override fun invoke(word: String): Flow<Resource<List<WordInfo>>> {
        if (word.isBlank()) {
            return flow {}
        }
        return wordInfoRepository.getWordInfos(word)
    }
}
