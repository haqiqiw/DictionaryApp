package com.example.dictionaryapp.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dictionaryapp.data.local.WordInfoDatabase
import com.example.dictionaryapp.data.local.dao.WordInfoDao
import com.example.dictionaryapp.data.remote.service.DictionaryService
import com.example.dictionaryapp.data.remote.service.ServiceBuilder
import com.example.dictionaryapp.data.repository.WordInfoRepository
import com.example.dictionaryapp.data.repository.WordInfoRepositoryImpl
import com.example.dictionaryapp.domain.usecase.GetWordInfoUseCase
import com.example.dictionaryapp.domain.usecase.GetWordInfoUseCaseImpl
import com.example.dictionaryapp.util.DispatchersProvider
import com.example.dictionaryapp.util.DispatchersProviderImpl

class MainViewModelFactory(
    private val context: Context,
    private val dispatchersProvider: DispatchersProvider = DispatchersProviderImpl(),
    private val dictionaryService: DictionaryService = ServiceBuilder.dictionaryService,
    private val wordInfoDao: WordInfoDao = WordInfoDatabase.create(context).wordInfoDao(),
    private val wordInfoRepository: WordInfoRepository = WordInfoRepositoryImpl(dictionaryService, wordInfoDao),
    private val getWordInfoUseCase: GetWordInfoUseCase = GetWordInfoUseCaseImpl(wordInfoRepository)
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(dispatchersProvider, getWordInfoUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
