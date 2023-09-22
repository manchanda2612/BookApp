package com.neeraj.presentation.uiutils

import androidx.annotation.DrawableRes
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

@Composable
fun DisplayBookImageFromUrl(
    imageUrl: String,
    modifier: Modifier,
    @DrawableRes placeholderImage: Int,
    @DrawableRes errorImage: Int,
    scale: Scale,
    contentScale : ContentScale,
    contentDescription : String,
    diskCachePolicy : CachePolicy,
    ) {

    Row(verticalAlignment = Alignment.CenterVertically) {

        val bookImage = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .apply(block = fun ImageRequest.Builder.() {
                    error(errorImage)
                    placeholder(placeholderImage)
                    scale(scale)
                    diskCachePolicy(diskCachePolicy)
                }).build()
        )

        Image(
            painter = bookImage,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}