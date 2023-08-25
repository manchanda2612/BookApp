package com.neeraj.booksapp

import android.util.Log
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

    private lateinit var mGetBookListUseCase : GetBookListUseCase
    private lateinit var mViewModel : BookListViewModel
    private val mTestDispatcher = StandardTestDispatcher()
    private val mBookListModelFile = "src/test/res/bookListModel.json"

    @Before
    fun setUp() {
        Dispatchers.setMain(mTestDispatcher)
        mGetBookListUseCase = mockk()
        mViewModel = BookListViewModel(mGetBookListUseCase)
    }

    @After
    fun windUp() {
        Dispatchers.resetMain()
        mTestDispatcher.cancel()
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
            Files.readAllBytes(Paths.get(mBookListModelFile))
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
        coEvery { mGetBookListUseCase() } returns result

        // When
        mViewModel = BookListViewModel(mGetBookListUseCase)

        // Then
        coVerify { mGetBookListUseCase() }
    }


}