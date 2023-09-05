package com.neeraj.booksapp

import com.neeraj.booksapp.data.mapper.BookMapper
import com.neeraj.booksapp.data.model.BooksDetailResponseModel
import com.neeraj.booksapp.data.model.BooksListResponseModel
import com.neeraj.booksapp.data.network.ApiService
import com.neeraj.booksapp.data.repository.BookRepositoryImpl
import com.neeraj.booksapp.domain.respository.BookRepository
import com.neeraj.booksapp.testutil.TestUtils
import com.neeraj.booksapp.testutil.bookDetailModelFile
import com.neeraj.booksapp.testutil.bookDetailResponseModelFile
import com.neeraj.booksapp.testutil.bookId
import com.neeraj.booksapp.testutil.bookListModelFile
import com.neeraj.booksapp.testutil.bookListResponseModelFile
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class BookRepositoryImplTest {

    // Mock dependencies
    private val apiService: ApiService = mockk()
    private val bookMapper: BookMapper = mockk()
    private lateinit var bookRepository: BookRepository

    @Before
    fun setUp() {
        bookRepository = BookRepositoryImpl(apiService, bookMapper)
    }

    @Test
    fun `when getBooksList is called and internet is available, it should return a list of books`() = runTest {
        // Given
        val bookListResponseModel = TestUtils.parseJSONToResponse<BooksListResponseModel>(TestUtils.readJSONFromResource(bookListResponseModelFile))
        val expectedBooksList = TestUtils.parseJSONToBookList(TestUtils.readJSONFromResource(bookListModelFile))

            // Mock the behavior of ApiService and BookMapper
        coEvery { apiService.getBooksList() } returns bookListResponseModel
        coEvery { bookListResponseModel.body()?.let { bookMapper.getBooksList(it) } } returns expectedBooksList

        // When
        val result = bookRepository.getBooksList()

        // Then
        coEvery { apiService.getBooksList() }
        coEvery { bookListResponseModel.body()?.let { bookMapper.getBooksList(it) } }

        // Assert that the result is a Resources.Success containing the expectedBooksList
        assertEquals(expectedBooksList, result)
    }

    @Test
    fun `when getBookDetail is called and internet is available, it should return a book detail`() = runTest {
        // Given
        val bookDetailResponseModel = TestUtils.parseJSONToResponse<BooksDetailResponseModel>(TestUtils.readJSONFromResource(bookDetailResponseModelFile))
        val expectedBookDetail = TestUtils.parseJSONToBookDetail(TestUtils.readJSONFromResource(bookDetailModelFile))

            // Mock the behavior of ApiService and BookMapper
        coEvery { apiService.getBookDetail(bookId) } returns bookDetailResponseModel
        coEvery { bookDetailResponseModel.body()?.let { bookMapper.getBookDetail(it) } } returns expectedBookDetail

        // When
        val result = bookRepository.getBookDetail(bookId)

        // Then
        coEvery { apiService.getBookDetail(bookId) }
        coEvery { bookDetailResponseModel.body()?.let { bookMapper.getBookDetail(it) } }

        // Assert that the result is a Resources.Success containing the expectedBookDetail
        assertEquals(expectedBookDetail, result)
    }



}