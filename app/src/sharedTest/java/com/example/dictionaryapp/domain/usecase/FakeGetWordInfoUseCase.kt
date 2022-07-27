package com.example.dictionaryapp.domain.usecase

import com.example.dictionaryapp.domain.model.WordInfo
import com.example.dictionaryapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetWordInfoUseCase : GetWordInfoUseCase {

    var flow = flow<Resource<List<WordInfo>>> {}
    override fun invoke(word: String): Flow<Resource<List<WordInfo>>> {
        return flow
    }
}
