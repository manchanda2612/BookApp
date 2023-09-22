package com.neeraj.data.mapper

import com.neeraj.common.Resources
import com.neeraj.data.model.BooksDetailResponseModel
import com.neeraj.data.model.BooksListResponseModel
import com.neeraj.domain.model.BookDetailModel
import com.neeraj.domain.model.BooksListModel
import javax.inject.Inject

/**
 * @author Neeraj Manchanda
 * This class is responsible for mapping data from response models (e.g., BooksListResponseModel, BooksDetailResponseModel) to application's domain models (BooksListModel, BookDetailModel).
 * It basically transforming network data into a format that's more suitable for application's needs.
 */
class BookMapper @Inject constructor() {

    fun getBooksList(booksListResponseModel: BooksListResponseModel) : Resources<List<BooksListModel>> {
        val bookList = mutableListOf<BooksListModel>()
        var id: String
        booksListResponseModel.items.forEach { item ->
            item.volumeInfo.let { volumeInfo ->
                id = item.id
                val book = BooksListModel(
                    id,
                    volumeInfo.title,
                    volumeInfo.authors.joinToString(", "),
                    volumeInfo.imageLinks.thumbnail.replace("http://", "https://")
                )
                bookList.add(book)
            }
        }
        return Resources.Success(bookList)
    }


    fun getBookDetail(bookDetailResponseModel : BooksDetailResponseModel) : Resources<BookDetailModel> {
        return bookDetailResponseModel.volumeInfo.let { volumeInfo ->
            Resources.Success(
                   BookDetailModel(
                    volumeInfo.title,
                    volumeInfo.subtitle ?: "Subtitle not available",
                    volumeInfo.authors.joinToString(", "),
                    volumeInfo.publisher,
                    volumeInfo.publishedDate,
                    volumeInfo.imageLinks.thumbnail.replace("http://", "https://")
                )
            )
        }
    }
}