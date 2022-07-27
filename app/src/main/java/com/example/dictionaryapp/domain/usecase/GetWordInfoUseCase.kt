package com.example.dictionaryapp.domain.usecase

import com.example.dictionaryapp.domain.model.WordInfo
import com.example.dictionaryapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetWordInfoUseCase {
    operator fun invoke(word: String): Flow<Resource<List<WordInfo>>>
}
