package com.neeraj.booksapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.neeraj.booksapp.R
import com.neeraj.booksapp.common.Resources
import com.neeraj.booksapp.domain.model.BookDetailModel
import com.neeraj.booksapp.presentation.viewmodel.BookDetailViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import coil.request.CachePolicy
import coil.size.Scale
import com.neeraj.booksapp.presentation.uiutils.Dimens
import com.neeraj.booksapp.presentation.uiutils.DisplayBookImageFromUrl
import com.neeraj.booksapp.presentation.uiutils.ShowProgressBar
import com.neeraj.booksapp.presentation.uiutils.ShowErrorMessage
import com.neeraj.booksapp.presentation.uiutils.ShowToolbar


/**
 * @author Neeraj Manchanda
 * It is a Composable function that represents the UI for displaying book details using Jetpack Compose.
 */
@Composable
fun BookDetailScreen(bookId : String, navController: NavController) {

    val viewModel: BookDetailViewModel = hiltViewModel()
    val bookDetailInfo = viewModel.bookDetail.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getBookDetail(bookId)
    }

    Column(
        modifier = Modifier.fillMaxWidth()) {
        ShowToolbar(stringResource(R.string.book_info), true) { navController.popBackStack() }
        when (bookDetailInfo.value) {
            is Resources.Loading -> ShowProgressBar()
            is Resources.Success -> ShowBookDetailScreen((bookDetailInfo.value as Resources.Success<BookDetailModel>).data)
            is Resources.Failure -> ShowErrorMessage(bookDetailInfo.value.toString())
        }
    }
}

@Composable
fun ShowBookDetailScreen(bookDetailModel: BookDetailModel) {

        Column(modifier = Modifier.padding(Dimens.ten_dp)) {
            DisplayBookImageFromUrl(
                bookDetailModel.thumbnail,
                Modifier.fillMaxWidth().height(Dimens.two_hundred_dp),
                R.drawable.ic_book_placeholder,
                R.drawable.ic_book_placeholder,
                Scale.FILL,
                ContentScale.Fit,
                stringResource(R.string.book_image),
                CachePolicy.ENABLED
            )

            Spacer(modifier = Modifier.height(Dimens.eight_dp))

            Text(
                text = bookDetailModel.bookTitle,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(Dimens.four_dp))

            Text(
                text = bookDetailModel.bookSubtitle,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(Dimens.four_dp))

            Text(
                text = stringResource(R.string.written_by, bookDetailModel.bookAuthors),
                style = MaterialTheme.typography.bodyMedium
            )


            Spacer(modifier = Modifier.width(Dimens.four_dp))
            Text(
                text = stringResource(R.string.published_by, bookDetailModel.publisher),
                style = MaterialTheme.typography.bodyMedium,
            )


            Spacer(modifier = Modifier.height(Dimens.four_dp))

            Text(
                text = stringResource(R.string.published_on, bookDetailModel.publishDate),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
