package com.neeraj.booksapp

import com.neeraj.booksapp.common.Constants
import com.neeraj.booksapp.common.DataError
import com.neeraj.booksapp.common.Resources
import com.neeraj.booksapp.domain.respository.BookRepository
import com.neeraj.booksapp.domain.usecases.GetBookDetailUseCase
import com.neeraj.booksapp.testutil.TestUtils
import com.neeraj.booksapp.testutil.bookDetailModelFile
import com.neeraj.booksapp.testutil.bookId
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetBookDetailUseCaseTest {

    // Mock dependencies
    private val bookRepository: BookRepository = mockk()
    private lateinit var getBookDetailUseCase: GetBookDetailUseCase

    @Before
    fun setUp() {
        getBookDetailUseCase = GetBookDetailUseCase(bookRepository)
    }

    @Test
    fun `when invoke is called and repository returns success, it should return success`() = runTest {

        val bookDetail = TestUtils.parseJSONToBookDetail(TestUtils.readJSONFromResource(bookDetailModelFile))

        // Given
        coEvery { bookRepository.getBookDetail(bookId) } returns bookDetail

        // When
        val result = getBookDetailUseCase(bookId)

        // Then
        coEvery { bookRepository.getBookDetail(bookId) }

        // Assert that the result is success
        assertEquals(bookDetail, result)
    }

    @Test
    fun `when invoke is called and repository returns error, it should return error`() = runTest {
        // Given
        // Mock the behavior of the bookRepository's getBookDetail method to return an error
        coEvery { bookRepository.getBookDetail(bookId) } returns Resources.Failure(DataError(Constants.ErrorMessage))

        // When
        val result = getBookDetailUseCase(bookId)

        // Then
        coEvery { bookRepository.getBookDetail(bookId) }

        // Assert that the result is an error
        assertEquals(Constants.ErrorMessage, (result as Resources.Failure).exception.message)
    }

    @Test
    fun `when invoke is called and repository returns loading, it should return loading`() = runTest {
        // Given
        // Mock the behavior of the bookRepository's getBookDetail method to return loading
        coEvery { bookRepository.getBookDetail(bookId) } returns Resources.Loading

        // When
        val result = getBookDetailUseCase(bookId)

        // Then
        coEvery { bookRepository.getBookDetail(bookId) }

        // Assert that the result is loading
        assertEquals(Resources.Loading, result)
    }
}
