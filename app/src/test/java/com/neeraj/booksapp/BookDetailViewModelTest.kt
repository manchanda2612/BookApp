package com.neeraj.booksapp

import com.neeraj.booksapp.common.Constants
import com.neeraj.booksapp.common.DataError
import com.neeraj.booksapp.common.Resources
import com.neeraj.booksapp.presentation.viewmodel.BookDetailViewModel
import com.neeraj.booksapp.testutil.TestUtils
import com.neeraj.booksapp.testutil.bookDetailModelFile
import com.neeraj.booksapp.testutil.bookId
import com.neeraj.domain.usecases.GetBookDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltViewModel
class BookDetailViewModelTest {

    // Mock dependencies
    private val bookDetailUseCase: GetBookDetailUseCase = mockk()
    private lateinit var viewModel: BookDetailViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = BookDetailViewModel(bookDetailUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `when getBookDetail is called, it should call bookDetailUseCase`() = runTest {

        // Given
        coEvery { bookDetailUseCase(bookId) } returns TestUtils.parseJSONToBookDetail(TestUtils.readJSONFromResource(bookDetailModelFile))

        // When
        viewModel.getBookDetail(bookId)

        // Then
        coVerify { bookDetailUseCase.invoke(bookId) }
    }

    @Test
    fun `when bookDetailUseCase returns success, bookDetail should have success state`() = runTest {

        val bookDetail = TestUtils.parseJSONToBookDetail(TestUtils.readJSONFromResource(bookDetailModelFile))

        // Given
        coEvery { bookDetailUseCase.invoke(bookId) } returns bookDetail

        // When
        viewModel.getBookDetail(bookId)

        // Then
        viewModel.bookDetail.collect { result ->
            assertTrue(result is Resources.Success)
            assertEquals(bookDetail, result)
        }
    }

    @Test
    fun `when bookDetailUseCase returns loading, bookDetail should have loading state`() = runTest {
        // Given
        coEvery { bookDetailUseCase.invoke(bookId) } returns Resources.Loading

        // When
        viewModel.getBookDetail(bookId)

        // Then
        viewModel.bookDetail.collect { result ->
            assertTrue(result is Resources.Loading)
        }
    }

    @Test
    fun `when bookDetailUseCase returns error, bookDetail should have error state`() = runTest {
        // Given
        coEvery { bookDetailUseCase.invoke(bookId) } returns Resources.Failure(DataError(Constants.ErrorMessage))

        // When
        viewModel.getBookDetail(bookId)

        // Then
        viewModel.bookDetail.collect { result ->
            assertTrue(result is Resources.Failure)
            assertEquals(Constants.ErrorMessage, (result as Resources.Failure).exception.message)
        }
    }
}
