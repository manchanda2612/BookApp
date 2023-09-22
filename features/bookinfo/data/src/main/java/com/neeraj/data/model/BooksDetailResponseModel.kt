package com.neeraj.data.model

/**
 * @author Neeraj Manchanda
 * This is a data class that representing a response from an API that provides detailed information about a book.
 */
data class BooksDetailResponseModel (
    val volumeInfo: VolumeInfo
) {

    data class VolumeInfo (
        val title: String = "",
        val subtitle: String? = "",
        val authors: List<String> = emptyList(),
        val publisher: String = "",
        val publishedDate: String = "",
        val imageLinks: ImageLinks
    )

    data class ImageLinks (
        val thumbnail: String = ""
    )

}
