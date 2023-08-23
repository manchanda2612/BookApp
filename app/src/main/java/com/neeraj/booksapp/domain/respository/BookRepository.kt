package com.neeraj.booksapp.domain.respository

import com.neeraj.booksapp.common.Resource
import com.neeraj.booksapp.domain.model.BookDetailModel
import com.neeraj.booksapp.domain.model.BooksListModel

/**
 * @author Neeraj Manchanda
 * The BookRepository interface defines the contract for fetching lists of books and book details.
 * It returns results wrapped in the Resource class, which encapsulates different possible outcomes of these operations.
 */
interface BookRepository {

    suspend fun getBooksList() : Resource<List<BooksListModel>>
    suspend fun getBookDetail(bookId : String) : Resource<BookDetailModel>
}