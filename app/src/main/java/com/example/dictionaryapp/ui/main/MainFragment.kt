package com.example.dictionaryapp.ui.main

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dictionaryapp.databinding.MainFragmentBinding
import com.example.dictionaryapp.ui.collectLifecycleFlow
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    @VisibleForTesting
    lateinit var viewModel: MainViewModel
    private lateinit var wordInfoAdapter: WordInfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, MainViewModelFactory(requireContext()))[MainViewModel::class.java]

        setUpView()
        renderUiState()
    }

    private fun setUpView() {
        binding.etSearch.setText(viewModel.searchQuery.value)
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                viewModel.onSearchQuery(binding.etSearch.text.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etSearch.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.onSearchQuery(binding.etSearch.text.toString())
                hideSoftKeyboard(textView)
                return@setOnEditorActionListener true
            }
            false
        }

        wordInfoAdapter = WordInfoAdapter { wordInfo ->
            viewModel.onUiEvent(
                MainViewModel.UiEvent.ShowSnackbar(
                    "Selected word: ${wordInfo.word} (${wordInfo.phonetic})"
                )
            )
        }
        binding.recyclerView.adapter = wordInfoAdapter
    }

    private fun renderUiState() {
        collectLifecycleFlow(viewModel.uiState) { uiState ->
            if (uiState.isLoadingState) {
                renderLoading()
            } else {
                renderWordInfoList(uiState)
            }
        }
        collectLifecycleFlow(viewModel.eventFlow) { event ->
            when (event) {
                is MainViewModel.UiEvent.ShowSnackbar -> {
                    Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun renderWordInfoList(uiState: MainUiState) {
        binding.progressBar.isVisible = false
        binding.recyclerView.isVisible = true
        wordInfoAdapter.submitList(uiState.wordInfos)
    }

    private fun renderLoading() {
        binding.progressBar.isVisible = true
        binding.recyclerView.isVisible = false
    }

    private fun hideSoftKeyboard(view: View): Boolean {
        val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
