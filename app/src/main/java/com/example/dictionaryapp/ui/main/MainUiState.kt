package com.example.dictionaryapp.ui.main

import com.example.dictionaryapp.domain.model.WordInfo

data class MainUiState(
    val wordInfos: List<WordInfo> = listOf(),
    val isLoading: Boolean = false
) {
    val isLoadingState get() = isLoading && wordInfos.isEmpty()
}
