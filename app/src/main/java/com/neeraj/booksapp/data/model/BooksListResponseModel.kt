package com.neeraj.booksapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author Neeraj Manchanda
 * This is a data class that representing a response from an API that provides a list of books.
 */
data class BooksListResponseModel (

    @SerializedName("items")
    @Expose
    var items: List<Item>? = null
) {

    data class Item (

        @SerializedName("id")
        @Expose
        var id: String? = null,

        @SerializedName("volumeInfo")
        @Expose
        var volumeInfo: VolumeInfo? = null

    )

    data class VolumeInfo (
        @SerializedName("title")
        @Expose
        var title: String? = null,

        @SerializedName("subtitle")
        @Expose
        var subtitle: String? = null,

        @SerializedName("authors")
        @Expose
        var authors: List<String>? = null,

        @SerializedName("publisher")
        @Expose
        var publisher: String? = null,

        @SerializedName("publishedDate")
        @Expose
        var publishedDate: String? = null,

        @SerializedName("description")
        @Expose
        var description: String? = null,

        @SerializedName("averageRating")
        @Expose
        var averageRating: String? = null,

        @SerializedName("ratingsCount")
        @Expose
        var ratingsCount: String? = null,

        @SerializedName("imageLinks")
        @Expose
        var imageLinks: ImageLinks? = null
    )

    data class ImageLinks (
        @SerializedName("smallThumbnail")
        @Expose
        var smallThumbnail: String? = null,

        @SerializedName("thumbnail")
        @Expose
        var thumbnail: String? = null
    )
}
