package com.neeraj.domain.model

/**
 * @author Neeraj Manchanda
 * It is a data class representing a mapped version of BooksDetailResponseModel.
 * This class likely corresponds to the data that we use in UI/Presentation layer would use to display book details in a user-friendly format.
 */
data class BookDetailModel (
    val bookTitle : String,
    val bookSubtitle : String,
    val bookAuthors : String,
    val publisher : String,
    val publishDate : String,
    val thumbnail : String
)