package com.sample.chaitanyasampleapp

import app.cash.turbine.test
import com.sample.chaitanyasampleapp.data.model.fakeData
import com.sample.chaitanyasampleapp.data.repository.NewsRepository
import com.sample.chaitanyasampleapp.domain.usecase.GetTopHeadlinesUseCase
import com.sample.chaitanyasampleapp.presentation.viewmodel.NewsViewModel
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private val testScope: TestScope = TestScope()

    private lateinit var viewModel: NewsViewModel

    private val repository: NewsRepository = mockk {
        every { getTopHeadlines("us") } returns flow {
            emit(Result.success(fakeData))
        }
    }

    private fun initializeViewModel() {
        viewModel = NewsViewModel(getTopHeadlinesUseCase = GetTopHeadlinesUseCase(repository))
    }

    private fun startTest(
        testBody: suspend TestScope.() -> Unit,
    ) {
        testScope.runTest(testBody = testBody)
    }

    private val testDispatcher = TestCoroutineDispatcher() // Create a test dispatcher

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        // Set the Main dispatcher to use the test dispatcher
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        // Reset the Main dispatcher after the test
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines() // Cleanup test dispatcher
    }

    @Test
    fun `Get NewsArticles from api `() {
        startTest {
            initializeViewModel()
            viewModel.state.test {
                with(awaitItem()) {
                    articles.shouldBe(fakeData)
                    error.shouldBeNull()
                    isLoading.shouldBeFalse()
                }
            }
        }
    }
}