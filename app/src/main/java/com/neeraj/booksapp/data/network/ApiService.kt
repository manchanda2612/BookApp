package com.neeraj.booksapp.data.network

import com.neeraj.booksapp.BuildConfig
import com.neeraj.booksapp.data.model.BooksDetailResponseModel
import com.neeraj.booksapp.data.model.BooksListResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Neeraj Manchanda
 * An interface defines the endpoints for making API calls to retrieve a list of books and book details.
 */
interface ApiService {

        @GET("volumes")
        suspend fun getBooksList(
            @Query("key") key: String = BuildConfig.API_KEY,
            @Query("q") query: String = "Kotlin",
        ): Response<BooksListResponseModel>

        @GET("volumes/{bookId}")
        suspend fun getBookDetail(
            @Path("bookId") bookId : String,
            @Query("key") key : String = BuildConfig.API_KEY,
        ): Response<BooksDetailResponseModel>


}