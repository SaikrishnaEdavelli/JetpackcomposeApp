package com.sample.chaitanyasampleapp.domain.usecase

import com.sample.chaitanyasampleapp.data.model.Article
import com.sample.chaitanyasampleapp.data.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(country: String): Flow<Result<List<Article>>> = repository.getTopHeadlines(country)
}