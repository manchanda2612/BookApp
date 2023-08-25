package com.neeraj.booksapp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import com.neeraj.booksapp.R


@Composable
fun DisplayBookImageFromUrl(imageUrl: String, modifier: Modifier, contentDescription : String) {

    Row(verticalAlignment = Alignment.CenterVertically) {

        val bookImage = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .apply(block = fun ImageRequest.Builder.() {
                    error(R.drawable.ic_book_placeholder)
                    placeholder(R.drawable.ic_book_placeholder)
                    scale(Scale.FILL)
                    diskCachePolicy(CachePolicy.ENABLED)
                }).build()
        )

        Image(
            painter = bookImage,
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
            modifier = modifier
        )

    }
}