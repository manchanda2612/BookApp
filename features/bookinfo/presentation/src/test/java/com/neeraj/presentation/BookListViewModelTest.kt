package com.neeraj.presentation

import com.neeraj.common.Constants
import com.neeraj.common.DataError
import com.neeraj.common.Resources
import com.neeraj.presentation.viewmodel.BookListViewModel
import com.neeraj.presentation.testutil.TestUtils
import com.neeraj.presentation.testutil.bookListModelFile
import com.neeraj.domain.usecases.GetBookListUseCase
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
class BookListViewModelTest {

    private lateinit var getBookListUseCase : GetBookListUseCase
    private lateinit var viewModel : BookListViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getBookListUseCase = mockk()
        viewModel = BookListViewModel(getBookListUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

     @Test
     fun `when viewModel is created, it should call getBookListUseCase`() = runTest {

         // Given
         coEvery { getBookListUseCase() } returns TestUtils.parseJSONToBookList(TestUtils.readJSONFromResource(bookListModelFile))

         // When
         viewModel = BookListViewModel(getBookListUseCase)

         // Then
         coVerify { getBookListUseCase() }

     }


    @Test
    fun `when getBookListUseCase returns success, bookListViewModel should have success state`() =
        runTest {

            val bookList = TestUtils.parseJSONToBookList(TestUtils.readJSONFromResource(bookListModelFile))

            // Given
            coEvery { getBookListUseCase.invoke() } returns bookList

            // When
            viewModel = BookListViewModel(getBookListUseCase)

            // Then
            viewModel.bookListViewModel.collect { result ->
                assertTrue(result is Resources.Success)
                assertEquals(bookList, result)
            }
        }


    @Test
    fun `when getBookListUseCase returns loading, mBookList should have loading state`() =
        runTest {
            // Given
            coEvery { getBookListUseCase.invoke() } returns Resources.Loading

            // When
            viewModel = BookListViewModel(getBookListUseCase)

            // Then
            viewModel.bookListViewModel.collect { result ->
                assertTrue(result is Resources.Loading)
            }
        }

    @Test
    fun `when getBookListUseCase returns error, bookListViewModel should have error state`() =
        runTest {
            // Given
            coEvery { getBookListUseCase.invoke() } returns Resources.Failure(DataError(Constants.ErrorMessage))

            // When
            viewModel = BookListViewModel(getBookListUseCase)

            // Then
            viewModel.bookListViewModel.collect { result ->
                assertTrue(result is Resources.Failure)
                assertEquals(Constants.ErrorMessage, (result as Resources.Failure).exception.message)
            }
        }
}