package com.example.dictionaryapp.data.repository

import app.cash.turbine.test
import com.example.dictionaryapp.data.local.dao.FakeWordInfoDao
import com.example.dictionaryapp.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.data.remote.dto.LicenseDto
import com.example.dictionaryapp.data.remote.dto.WordInfoDto
import com.example.dictionaryapp.data.remote.service.DictionaryService
import com.example.dictionaryapp.domain.model.License
import com.example.dictionaryapp.rule.TestCoroutineRule
import com.example.dictionaryapp.util.Resource
import com.google.common.truth.Truth.assertThat
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

@ExperimentalCoroutinesApi
class WordInfoRepositoryImplTest {

    @get:Rule
    val testCoroutineRule: TestCoroutineRule = TestCoroutineRule()

    private lateinit var wordInfoRepository: WordInfoRepository

    private lateinit var dictionaryService: DictionaryService

    private lateinit var wordInfoDao: FakeWordInfoDao

    @Before
    fun setUp() {
        dictionaryService = mockk(relaxed = true, relaxUnitFun = true)
        wordInfoDao = FakeWordInfoDao()
        wordInfoRepository = WordInfoRepositoryImpl(dictionaryService, wordInfoDao)
    }

    @After
    fun tearDown() {
        clearMocks(dictionaryService)
    }

    @Test
    fun `getWordInfos throws HttpException is correct`() = testCoroutineRule.runTest {
        // Given
        wordInfoDao.getWordInfosResult = listOf(
            WordInfoEntity(
                null, License("abc", "def"), listOf(), "bank", listOf(), listOf(), "bank"
            )
        )
        coEvery { dictionaryService.getWordInfos(any()) } throws HttpException(mockk(relaxed = true))

        // When
        wordInfoRepository.getWordInfos("bank").test {
            // Then
            with(awaitItem() as Resource.Loading) {
                assertThat(data).isEqualTo(null)
                assertThat(message).isEqualTo(null)
            }
            with(awaitItem() as Resource.Loading) {
                assertThat(data?.size).isEqualTo(1)
                assertThat(message).isEqualTo(null)
            }
            with(awaitItem() as Resource.Error) {
                assertThat(data?.size).isEqualTo(1)
                assertThat(message).isEqualTo("Oops, something went wrong!")
            }
            with(awaitItem() as Resource.Success) {
                assertThat(data?.size).isEqualTo(1)
                assertThat(message).isEqualTo(null)
            }
            awaitComplete()
        }
    }

    @Test
    fun `getWordInfos throws IOException is correct`() = testCoroutineRule.runTest {
        // Given
        wordInfoDao.getWordInfosResult = listOf(
            WordInfoEntity(
                null, License("abc", "def"), listOf(), "bank", listOf(), listOf(), "bank"
            )
        )
        coEvery { dictionaryService.getWordInfos(any()) } throws IOException("Error")

        // When
        wordInfoRepository.getWordInfos("bank").test {
            // Then
            with(awaitItem() as Resource.Loading) {
                assertThat(data).isEqualTo(null)
                assertThat(message).isEqualTo(null)
            }
            with(awaitItem() as Resource.Loading) {
                assertThat(data?.size).isEqualTo(1)
                assertThat(message).isEqualTo(null)
            }
            with(awaitItem() as Resource.Error) {
                assertThat(data?.size).isEqualTo(1)
                assertThat(message).isEqualTo("Couldn't reach server, check your internet connection.")
            }
            with(awaitItem() as Resource.Success) {
                assertThat(data?.size).isEqualTo(1)
                assertThat(message).isEqualTo(null)
            }
            awaitComplete()
        }
    }

    @Test
    fun `getWordInfos success is correct`() = testCoroutineRule.runTest {
        // Given
        wordInfoDao.getWordInfosResult = listOf(
            WordInfoEntity(
                null, License("abc", "def"), listOf(), "bank", listOf(), listOf(), "bank"
            )
        )
        coEvery { dictionaryService.getWordInfos(any()) } returns listOf(
            WordInfoDto(
                LicenseDto("abc", "def"), listOf(), "bank", listOf(), listOf(), "bank"
            )
        )

        // When
        wordInfoRepository.getWordInfos("bank").test {
            // Then
            with(awaitItem() as Resource.Loading) {
                assertThat(data).isEqualTo(null)
                assertThat(message).isEqualTo(null)
            }
            with(awaitItem() as Resource.Loading) {
                assertThat(data?.size).isEqualTo(1)
                assertThat(message).isEqualTo(null)
            }
            with(awaitItem() as Resource.Success) {
                assertThat(data?.size).isEqualTo(1)
                assertThat(message).isEqualTo(null)
            }
            awaitComplete()
        }
    }
}
