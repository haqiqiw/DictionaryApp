package com.example.dictionaryapp.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.domain.usecase.GetWordInfoUseCase
import com.example.dictionaryapp.util.DispatchersProvider
import com.example.dictionaryapp.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    private val dispatchers: DispatchersProvider,
    private val getWordInfoUseCase: GetWordInfoUseCase
) : ViewModel() {

    @VisibleForTesting
    val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    @VisibleForTesting
    val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchQueryJob: Job? = null

    fun onSearchQuery(query: String) {
        _searchQuery.value = query
        searchQueryJob?.cancel()
        searchQueryJob = viewModelScope.launch(dispatchers.io) {
            delay(500L)
            getWordInfoUseCase(query)
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _uiState.value = uiState.value.copy(
                                wordInfos = result.data.orEmpty(),
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            _uiState.value = uiState.value.copy(
                                wordInfos = result.data.orEmpty(),
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _uiState.value = uiState.value.copy(
                                wordInfos = result.data.orEmpty(),
                                isLoading = false
                            )
                            onUiEvent(
                                UiEvent.ShowSnackbar(
                                    result.message ?: "Unknown error."
                                )
                            )
                        }
                    }
                }
                .launchIn(this)
        }
    }

    fun onUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch(dispatchers.default) {
            _eventFlow.emit(uiEvent)
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}
