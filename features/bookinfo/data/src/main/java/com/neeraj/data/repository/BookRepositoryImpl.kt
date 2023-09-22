package com.neeraj.data.repository

import com.neeraj.common.Constants
import com.neeraj.common.IOError
import com.neeraj.common.DataError
import com.neeraj.common.InternetError
import com.neeraj.common.InternetUtil
import com.neeraj.common.Resources
import com.neeraj.data.mapper.BookMapper
import com.neeraj.data.network.ApiService
import com.neeraj.domain.model.BookDetailModel
import com.neeraj.domain.model.BooksListModel
import com.neeraj.domain.respository.BookRepository
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
    private val bookMapper: BookMapper,
    private val internetUtil: InternetUtil
) : BookRepository {


    override suspend fun getBooksList(): Resources<List<BooksListModel>> {
        return withContext(Dispatchers.IO) {
            if (internetUtil.isInternetAvailable()) {
                try {
                    val bookListResponse = apiService.getBooksList()
                    if (bookListResponse.isSuccessful) {
                        val bookListModel = bookListResponse.body()
                        return@withContext bookListModel?.let {
                            bookMapper.getBooksList(it)
                        } ?: Resources.Failure(
                            IOError(
                                bookListResponse.code(),
                                bookListResponse.errorBody()?.toString() ?: Constants.ErrorMessage
                            )
                        ) // Provide an empty list if bookListModel is null
                    } else {
                        return@withContext Resources.Failure(
                            IOError(
                                bookListResponse.code(),
                                bookListResponse.errorBody()?.string()
                            )
                        )
                    }
                } catch (exception: Exception) {
                    return@withContext Resources.Failure(
                        DataError(
                            exception.message ?: Constants.ErrorMessage
                        )
                    )
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