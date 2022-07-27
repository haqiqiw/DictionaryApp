package com.example.dictionaryapp.domain.usecase

import app.cash.turbine.test
import com.example.dictionaryapp.data.repository.FakeWordInfoRepository
import com.example.dictionaryapp.domain.model.License
import com.example.dictionaryapp.domain.model.WordInfo
import com.example.dictionaryapp.rule.TestCoroutineRule
import com.example.dictionaryapp.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetWordInfoUseCaseImplTest {

    @get:Rule
    val testCoroutineRule: TestCoroutineRule = TestCoroutineRule()

    private lateinit var getWordInfoUseCase: GetWordInfoUseCase

    private lateinit var wordInfoRepository: FakeWordInfoRepository

    @Before
    fun setUp() {
        wordInfoRepository = FakeWordInfoRepository()
        getWordInfoUseCase = GetWordInfoUseCaseImpl(wordInfoRepository)
    }

    @Test
    fun `getWordInfoUseCase invoke when word blank is correct`() = testCoroutineRule.runTest {
        // When
        getWordInfoUseCase("").test {
            // Then
            awaitComplete()
        }
    }

    @Test
    fun `getWordInfoUseCase invoke when word not blank is correct`() = testCoroutineRule.runTest {
        // Given
        val mockResource = Resource.Success(
            listOf(
                WordInfo(
                    License("abc", "def"), listOf(), "bank", listOf(), listOf(), "bank"
                )
            )
        )
        wordInfoRepository.getWordInfosFlow = flow { emit(mockResource) }

        // When
        getWordInfoUseCase("bank").test {
            // Then
            with(awaitItem() as Resource.Success) {
                assertThat(data?.size).isEqualTo(1)
                assertThat(message).isEqualTo(null)
            }
            awaitComplete()
        }
    }
}
