package com.neeraj.booksapp.data.model

/**
 * @author Neeraj Manchanda
 * This is a data class that representing a response from an API that provides a list of books.
 */
data class BooksListResponseModel (
    val items: List<Item> = emptyList()
) {
    data class Item (
        val id: String,
        val volumeInfo: VolumeInfo
    )

    data class VolumeInfo (
        val title: String = "",
        val authors: List<String> = emptyList(),
        val imageLinks: ImageLinks
    )

    data class ImageLinks (
        val thumbnail: String = ""
    )
}
