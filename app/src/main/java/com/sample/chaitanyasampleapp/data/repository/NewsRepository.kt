package com.sample.chaitanyasampleapp.data.repository

import com.sample.chaitanyasampleapp.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadlines(country: String): Flow<Result<List<Article>>>
}