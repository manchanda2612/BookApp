package com.neeraj.domain.respository

import com.neeraj.common.Resources
import com.neeraj.domain.model.BookDetailModel
import com.neeraj.domain.model.BooksListModel

/**
 * @author Neeraj Manchanda
 * The BookRepository interface defines the contract for fetching lists of books and book details.
 * It returns results wrapped in the Resource class, which encapsulates different possible outcomes of these operations.
 */
interface BookRepository {

    suspend fun getBooksList() : Resources<List<BooksListModel>>
    suspend fun getBookDetail(bookId : String) : Resources<BookDetailModel>
}