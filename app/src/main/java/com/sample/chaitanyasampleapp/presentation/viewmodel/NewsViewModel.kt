package com.sample.chaitanyasampleapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.chaitanyasampleapp.data.model.Article
import com.sample.chaitanyasampleapp.domain.usecase.GetTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(NewsState())
    val state: StateFlow<NewsState> = _state

    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle: StateFlow<Article?> = _selectedArticle

    private val articleFlowCache: MutableMap<String, Flow<Article?>> = mutableMapOf()

    fun getArticleById(id: String) {
        viewModelScope.launch {
            val cachedFlow = articleFlowCache[id] ?: getArticleByIdFlow(id).also {
                articleFlowCache[id] = it
            }

            cachedFlow.collect { article ->
                _selectedArticle.value = article
            }
        }
    }

    private fun getArticleByIdFlow(author: String): Flow<Article?> = flow {
        /* Check the first matching article found or not*/
        val article = _state.value.articles.firstOrNull { it.author == author }
        emit(article)
    }.shareIn(viewModelScope, SharingStarted.Lazily, replay = 1)

    init {
        fetchTopHeadlines()
    }

    fun fetchTopHeadlines(country: String = "us") {
        _state.value = _state.value.copy(isLoading = true)
        getTopHeadlinesUseCase(country)
            .onEach { result ->
                _state.value = when {
                    result.isSuccess -> NewsState(articles = result.getOrDefault(emptyList()))
                    else -> NewsState(error = result.exceptionOrNull()?.message ?: "Unknown Error")
                }
            }
            .launchIn(viewModelScope)
    }
}

data class NewsState(
    val articles: List<Article> = emptyList(),
    val error: String? = null,
    var isLoading: Boolean = false
)
