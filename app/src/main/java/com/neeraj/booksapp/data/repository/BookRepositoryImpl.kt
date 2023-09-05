package com.neeraj.booksapp.data.repository

import com.neeraj.booksapp.common.Constants
import com.neeraj.booksapp.common.IOError
import com.neeraj.booksapp.common.DataError
import com.neeraj.booksapp.common.InternetError
import com.neeraj.booksapp.common.InternetUtil
import com.neeraj.booksapp.common.Resources
import com.neeraj.booksapp.data.mapper.BookMapper
import com.neeraj.booksapp.data.network.ApiService
import com.neeraj.booksapp.domain.model.BookDetailModel
import com.neeraj.booksapp.domain.model.BooksListModel
import com.neeraj.booksapp.domain.respository.BookRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * @author Neeraj Manchanda
 *  It interacts with the ApiService to fetch lists of books and book details and uses the BookMapper to map the API responses to domain models.
 *  Additionally, it handles network availability using the isInternetAvailable() function.
 */
class BookRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val bookMapper: BookMapper
) : BookRepository {

    @Inject
    lateinit var internetUtil: InternetUtil

    override suspend fun getBooksList(): Resources<List<BooksListModel>> {
        return withContext(Dispatchers.IO) {
            if (internetUtil.isInternetAvailable()) {
                try {
                    val bookListResponse = apiService.getBooksList()
                    if (bookListResponse.isSuccessful) {
                        val bookListModel = bookListResponse.body()
                        return@withContext bookListModel?.let {
                            bookMapper.getBooksList(it)
                        } ?: Resources.Failure(IOError(bookListResponse.code(), bookListResponse.errorBody()?.toString() ?: Constants.ErrorMessage)) // Provide an empty list if bookListModel is null
                    } else {
                        return@withContext Resources.Failure(
                            IOError(
                                bookListResponse.code(),
                                bookListResponse.errorBody()?.string()
                            )
                        )
                    }
                } catch (exception: Exception) {
                    return@withContext Resources.Failure(DataError(exception.message ?: Constants.ErrorMessage))
                }
            } else {
                return@withContext Resources.Failure(InternetError(Constants.InternetErrorMessage))
            }
        }
    }


    override suspend fun getBookDetail(bookId: String): Resources<BookDetailModel> {
        return withContext(Dispatchers.IO) {
            if (internetUtil.isInternetAvailable()) {
                try {
                    val bookDetailResponse = apiService.getBookDetail(bookId)
                    if (bookDetailResponse.isSuccessful) {
                        val movieDetailModel = bookDetailResponse.body()
                        return@withContext movieDetailModel?.let {
                            bookMapper.getBookDetail(it)
                        } ?: Resources.Failure(
                            IOError(
                                bookDetailResponse.code(),
                                bookDetailResponse.errorBody()?.toString()
                            )
                        )
                    }
                } catch (e: IOException) {
                    return@withContext Resources.Failure(DataError(e.message))
                }
                return@withContext Resources.Failure(DataError(Constants.ErrorMessage))
            }
            else {
                return@withContext Resources.Failure(InternetError(Constants.InternetErrorMessage))
            }
        }
    }
}