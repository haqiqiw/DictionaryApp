package com.example.dictionaryapp.ui.main

import app.cash.turbine.test
import com.example.dictionaryapp.domain.model.License
import com.example.dictionaryapp.domain.model.WordInfo
import com.example.dictionaryapp.domain.usecase.FakeGetWordInfoUseCase
import com.example.dictionaryapp.rule.TestCoroutineRule
import com.example.dictionaryapp.util.FakeDispatchersProvider
import com.example.dictionaryapp.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val testCoroutineRule: TestCoroutineRule = TestCoroutineRule()

    private lateinit var mainViewModel: MainViewModel

    private lateinit var dispatchersProvider: FakeDispatchersProvider

    private lateinit var getWordInfoUseCase: FakeGetWordInfoUseCase

    @Before
    fun setUp() {
        dispatchersProvider = FakeDispatchersProvider(testCoroutineRule.getTestDispatcher())
        getWordInfoUseCase = FakeGetWordInfoUseCase()
        mainViewModel = MainViewModel(dispatchersProvider, getWordInfoUseCase)
    }

    @Test
    fun `onSearchQuery loading is correct`() = testCoroutineRule.runTest {
        // Given
        val resource = Resource.Loading(
            data = listOf(
                WordInfo(
                    License("abc", "def"), listOf(), "bank", listOf(), listOf(), "bank"
                )
            )
        )
        getWordInfoUseCase.flow = flow {
            emit(resource)
        }

        // When
        mainViewModel.onSearchQuery("bank")
        advanceUntilIdle()

        // then
        with(mainViewModel.uiState.value) {
            assertThat(wordInfos.size).isEqualTo(1)
            assertThat(isLoading).isEqualTo(true)
        }
    }

    @Test
    fun `onSearchQuery success is correct`() = testCoroutineRule.runTest {
        // Given
        val resource = Resource.Success(
            data = listOf(
                WordInfo(
                    License("abc", "def"), listOf(), "bank", listOf(), listOf(), "bank"
                )
            )
        )
        getWordInfoUseCase.flow = flow {
            emit(resource)
        }

        // When
        mainViewModel.onSearchQuery("bank")
        advanceUntilIdle()

        // then
        with(mainViewModel.uiState.value) {
            assertThat(wordInfos.size).isEqualTo(1)
            assertThat(isLoading).isEqualTo(false)
        }
    }

    @Test
    fun `onSearchQuery error is correct`() = testCoroutineRule.runTest {
        // Given
        val resource = Resource.Error(
            message = "Error",
            data = listOf(
                WordInfo(
                    License("abc", "def"), listOf(), "bank", listOf(), listOf(), "bank"
                )
            )
        )
        getWordInfoUseCase.flow = flow {
            emit(resource)
        }

        // When
        val job = launch {
            mainViewModel.onSearchQuery("bank")
            advanceUntilIdle()
        }

        // Then
        mainViewModel.eventFlow.test {
            job.join()

            with(awaitItem() as MainViewModel.UiEvent.ShowSnackbar) {
                assertThat(message).isEqualTo("Error")
            }
            with(mainViewModel.uiState.value) {
                assertThat(wordInfos.size).isEqualTo(1)
                assertThat(isLoading).isEqualTo(false)
            }

            job.cancel()
        }
    }

    @Test
    fun `onUiEvent is correct`() = testCoroutineRule.runTest {
        // Given
        val event = MainViewModel.UiEvent.ShowSnackbar("Message")

        // When
        val job = launch {
            mainViewModel.onUiEvent(event)
        }

        // Then
        mainViewModel.eventFlow.test {
            job.join()

            with(awaitItem() as MainViewModel.UiEvent.ShowSnackbar) {
                assertThat(message).isEqualTo("Message")
            }

            job.cancel()
        }
    }
}
