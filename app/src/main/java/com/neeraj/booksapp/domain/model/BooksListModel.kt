package com.neeraj.booksapp.domain.model


/**
 * @author Neeraj Manchanda
 * It is a data class representing a mapped version of BookListResponseModel.
 * This class will helpful to show list of books to user.
 */
data class BooksListModel (
    val bookId : String,
    val bookTitle : String,
    val bookAuthor : String,
    val smallThumbnail : String
)