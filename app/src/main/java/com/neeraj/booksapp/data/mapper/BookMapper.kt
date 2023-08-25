package com.neeraj.booksapp.data.mapper

import com.neeraj.booksapp.data.model.BooksDetailResponseModel
import com.neeraj.booksapp.data.model.BooksListResponseModel
import com.neeraj.booksapp.domain.model.BookDetailModel
import com.neeraj.booksapp.domain.model.BooksListModel
import javax.inject.Inject

/**
 * @author Neeraj Manchanda
 * This class is responsible for mapping data from response models (e.g., BooksListResponseModel, BooksDetailResponseModel) to application's domain models (BooksListModel, BookDetailModel).
 * It basically transforming network data into a format that's more suitable for application's needs.
 */
class BookMapper @Inject constructor() {

    fun getBooksList(booksListResponseModel: BooksListResponseModel) : List<BooksListModel> {
        val bookList = mutableListOf<BooksListModel>()
        var id: String
        booksListResponseModel.items?.forEach { item ->
            item.volumeInfo?.let { volumeInfo ->
                id = item.id
                val book = BooksListModel(
                    id,
                    volumeInfo.title.orEmpty(),
                    volumeInfo.authors?.joinToString(", ").orEmpty(),
                    volumeInfo.imageLinks?.thumbnail?.replace("http://", "https://").orEmpty()
                )
                book.let { bookList.add(it) }
            }
        }
        return bookList
    }


    fun getBookDetail(bookDetailListResponseModel : BooksDetailResponseModel) : BookDetailModel? {
        var bookDetail : BookDetailModel? = null
        bookDetailListResponseModel.volumeInfo?.let { volumeInfo ->
                   bookDetail = BookDetailModel(
                    volumeInfo.title.orEmpty(),
                    volumeInfo.subtitle.orEmpty(),
                    volumeInfo.authors?.joinToString(", ").orEmpty(),
                    volumeInfo.publisher.orEmpty(),
                    volumeInfo.publishedDate.orEmpty(),
                    volumeInfo.imageLinks?.thumbnail?.replace("http://", "https://").orEmpty(),
                    volumeInfo.ratingsCount.orEmpty()
                )
            }
        return bookDetail
    }

}