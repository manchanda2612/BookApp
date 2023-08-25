package com.neeraj.booksapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neeraj.booksapp.common.Resource
import com.neeraj.booksapp.domain.model.BooksListModel
import com.neeraj.booksapp.domain.use_cases.GetBookListUseCase
import com.neeraj.booksapp.presentation.view_model.BookListViewModel
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
class BookListViewModelTest {

    private lateinit var getBookListUseCase : GetBookListUseCase
    private lateinit var viewModel : BookListViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val bookListModelFile = "src/test/res/bookListModel.json"

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getBookListUseCase = mockk()
        viewModel = BookListViewModel(getBookListUseCase)
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

    private suspend fun parseJSONToBookList(jsonString: String): Resource<List<BooksListModel>> {
        return withContext(Dispatchers.Default) {
            try {
                val gson = Gson()
                val listType = object : TypeToken<List<BooksListModel>>() {}.type
                val bookList = gson.fromJson<List<BooksListModel>>(jsonString, listType)
                Resource.Success(bookList)
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred while parsing JSON")
            }
        }
    }

    @Test
    fun `when mViewModel is created, it should call mGetBookListUseCase`() = runTest {


        val jsonString = String(withContext(Dispatchers.IO) {
            Files.readAllBytes(Paths.get(bookListModelFile))
        })

        val result = try {
            val gson = Gson()
            val listType = object : TypeToken<List<BooksListModel>>() {}.type
            val bookList = gson.fromJson<List<BooksListModel>>(jsonString, listType)
            Resource.Success(bookList)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while parsing JSON")
        }
        // Given
        coEvery { getBookListUseCase.invoke() } returns result

        // When
        viewModel = BookListViewModel(getBookListUseCase)

        // Then
        coVerify { getBookListUseCase.invoke() }
    }


}