package com.sample.chaitanyasampleapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.chaitanyasampleapp.data.model.Article
import com.sample.chaitanyasampleapp.domain.usecase.GetTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NewsState())
    val state = _state.asStateFlow()

    init {
        fetchTopHeadlines()
    }

    fun fetchTopHeadlines(country: String = "us") {
        getTopHeadlinesUseCase(country).onEach { result ->
            _state.value = when {
                result.isSuccess -> NewsState(articles = result.getOrNull() ?: emptyList())
                else -> NewsState(error = result.exceptionOrNull()?.message ?: "Unknown Error")
            }
        }.launchIn(viewModelScope)
    }
}

data class NewsState(
    val articles: List<Article> = emptyList(),
    val error: String? = null,
    var isLoading: Boolean = false
)
