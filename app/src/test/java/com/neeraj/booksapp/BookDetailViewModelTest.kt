package com.neeraj.booksapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neeraj.booksapp.common.Resource
import com.neeraj.booksapp.domain.model.BookDetailModel
import com.neeraj.booksapp.domain.use_cases.GetBookDetailUseCase
import com.neeraj.booksapp.presentation.view_model.BookDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.nio.file.Files
import java.nio.file.Paths

@ExperimentalCoroutinesApi
@HiltViewModel
class BookDetailViewModelTest {

    // Mock dependencies
    private val bookDetailUseCase: GetBookDetailUseCase = mockk()
    private lateinit var viewModel: BookDetailViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val bookDetailModelFile = "src/test/res/bookDetailModel.json"
    private val bookId = "CzCWDwAAQBAJ"

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = BookDetailViewModel(bookDetailUseCase)
    }

    @After
    fun windUp() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    private suspend fun readJSONFromResource(resourceName: String): String {
        return  String(withContext(Dispatchers.IO) {
            Files.readAllBytes(Paths.get(resourceName))
        })
    }

    private suspend fun parseJSONToBookDetail(jsonString: String): Resource<BookDetailModel> {
        return withContext(Dispatchers.Default) {
            try {
                val gson = Gson()
                val listType = object : TypeToken<BookDetailModel>() {}.type
                val bookDetail = gson.fromJson<BookDetailModel>(jsonString, listType)
                Resource.Success(bookDetail)
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred while parsing JSON")
            }
        }
    }

    @Test
    fun `when getBookDetail is called, it should call bookDetailUseCase`() = runTest {

        val jsonString = readJSONFromResource(bookDetailModelFile)
        val result = parseJSONToBookDetail(jsonString)

        // Given
        coEvery { bookDetailUseCase.invoke(bookId) } returns result

        // When
        viewModel.getBookDetail(bookId)

        // Then
        coVerify { bookDetailUseCase.invoke(bookId) }
    }

   /* @Test
    fun `when bookDetailUseCase returns success, mBookDetail should have success state`() = runTest {
        // Given

        val bookDetail = *//* create mock book detail *//*
            coEvery { bookDetailUseCase.invoke(mBookId) } returns Resource.Success(bookDetail)

        // When
        viewModel.getBookDetail(mBookId)

        // Then
        viewModel.mBookDetail.collect { result ->
            assertTrue(result is Resource.Success)
            assertEquals(bookDetail, result.data)
        }
    }

    @Test
    fun `when bookDetailUseCase returns loading, mBookDetail should have loading state`() = runTest {
        // Given
        coEvery { bookDetailUseCase.invoke(mBookId) } returns Resource.Loading()

        // When
        viewModel.getBookDetail(mBookId)

        // Then
        viewModel.mBookDetail.collect { result ->
            assertTrue(result is Resource.Loading)
        }
    }

    @Test
    fun `when bookDetailUseCase returns error, mBookDetail should have error state`() = runTest {
        // Given
        val errorMessage = "An error occurred"
        coEvery { bookDetailUseCase.invoke(mBookId) } returns Resource.Error(errorMessage)

        // When
        viewModel.getBookDetail(mBookId)

        // Then
        viewModel.mBookDetail.collect { result ->
            assertTrue(result is Resource.Error)
            assertEquals(errorMessage, (result as Resource.Error).message)
        }
    }*/
}
