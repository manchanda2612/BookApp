package com.neeraj.booksapp.data.repository

import com.neeraj.booksapp.common.InternetUtil.Companion.isInternetAvailable
import com.neeraj.booksapp.common.Resource
import com.neeraj.booksapp.data.mapper.BookMapper
import com.neeraj.booksapp.data.network.ApiService
import com.neeraj.booksapp.domain.model.BookDetailModel
import com.neeraj.booksapp.domain.model.BooksListModel
import com.neeraj.booksapp.domain.respository.BookRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

/**
 * @author Neeraj Manchanda
 *  It interacts with the ApiService to fetch lists of books and book details and uses the BookMapper to map the API responses to domain models.
 *  Additionally, it handles network availability using the isInternetAvailable() function.
 */
class BookRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val bookMapper: BookMapper
) : BookRepository {

    override suspend fun getBooksList(): Resource<List<BooksListModel>> =
        withContext(Dispatchers.IO) {
            if (isInternetAvailable()) {
                try {
                    val bookListResponse = apiService.getBooksList()
                    if (bookListResponse.isSuccessful) {
                        val bookListModel = bookListResponse.body()
                        Resource.Success(bookListModel?.let {
                            bookMapper.getBooksList(it)
                        })
                    } else {
                        Resource.IOError(
                            bookListResponse.code(),
                            bookListResponse.errorBody()?.string()
                        )
                    }
                } catch (exception: Exception) {
                    Resource.Error(exception.message)
                }
            } else {
                Resource.InternetError()
            }
        }

    override suspend fun getBookDetail(bookId: String): Resource<BookDetailModel> {
        return coroutineScope {
            val internetAvailable = isInternetAvailable()

            if (!internetAvailable) {
                return@coroutineScope Resource.InternetError()
            }

            try {
                val bookDetailResponseDeferred = async(Dispatchers.IO) {
                    apiService.getBookDetail(bookId)
                }

                val bookDetailResponse = bookDetailResponseDeferred.await()

                if (bookDetailResponse.isSuccessful) {
                    val bookDetailModel = bookDetailResponse.body()
                    return@coroutineScope Resource.Success(bookDetailModel?.let {
                        bookMapper.getBookDetail(it)
                    })
                } else {
                    return@coroutineScope Resource.IOError(
                        bookDetailResponse.code(),
                        bookDetailResponse.errorBody()?.string()
                    )
                }
            } catch (exception: Exception) {
                return@coroutineScope Resource.Error(exception.message)
            }
        }
    }

}