package com.neeraj.domain

import com.neeraj.common.Constants
import com.neeraj.common.DataError
import com.neeraj.common.Resources
import com.neeraj.domain.respository.BookRepository
import com.neeraj.domain.testutil.TestUtils
import com.neeraj.domain.testutil.bookListModelFile
import com.neeraj.domain.usecases.GetBookListUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetBookListUseCaseTest {

    // Mock dependencies
    private val bookRepository: BookRepository = mockk()
    private lateinit var useCase: GetBookListUseCase

    @Before
    fun setup() {
        useCase = GetBookListUseCase(bookRepository)
    }

    @Test
    fun `when repository returns success, use case should return success`() = runTest {

        val bookList = TestUtils.parseJSONToBookList(TestUtils.readJSONFromResource(bookListModelFile))

        // Given
        coEvery { bookRepository.getBooksList() } returns bookList

        // When
        val result = useCase.invoke()

        // Then
        assertTrue(result is Resources.Success)
        assertEquals(bookList, result)
        coVerify { bookRepository.getBooksList() }
    }

    @Test
    fun `when repository returns loading, use case should return loading`() = runTest {
        // Given
        coEvery { bookRepository.getBooksList() } returns Resources.Loading

        // When
        val result = useCase.invoke()

        // Then
        assertTrue(result is Resources.Loading)
        coVerify { bookRepository.getBooksList() }
    }

    @Test
    fun `when repository returns error, use case should return error`() = runTest {
        // Given
        coEvery { bookRepository.getBooksList() } returns Resources.Failure(DataError(Constants.ErrorMessage))

        // When
        val result = useCase.invoke()

        // Then
        assertTrue(result is Resources.Failure)
        assertEquals(Constants.ErrorMessage, (result as Resources.Failure).exception.message)
        coVerify { bookRepository.getBooksList() }
    }
}
