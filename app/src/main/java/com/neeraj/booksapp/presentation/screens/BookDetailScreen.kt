package com.neeraj.booksapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.neeraj.booksapp.R
import com.neeraj.booksapp.common.Resource
import com.neeraj.booksapp.domain.model.BookDetailModel
import com.neeraj.booksapp.presentation.view_model.BookDetailViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.neeraj.booksapp.presentation.common.DisplayBookImageFromUrl
import com.neeraj.booksapp.presentation.common.ShowProgressBar
import com.neeraj.booksapp.presentation.common.ShowErrorMessage
import com.neeraj.booksapp.presentation.common.ShowToolbar


/**
 * @author Neeraj Manchanda
 * It is a Composable function that represents the UI for displaying book details using Jetpack Compose.
 */
@Composable
fun BookDetailScreen(bookId : String, navController: NavController) {

    val viewModel: BookDetailViewModel = hiltViewModel()
    val bookDetailInfo = viewModel.mBookDetail.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getBookDetail(bookId)
    }

    Column(
        modifier = Modifier.fillMaxWidth()) {
        ShowToolbar(stringResource(R.string.book_info), true) { navController.popBackStack() }
        when (bookDetailInfo.value) {
            is Resource.Loading -> ShowProgressBar()
            is Resource.Success -> bookDetailInfo.value.data?.let {ShowBookDetailScreen(it) }

            is Resource.Error -> bookDetailInfo.value.message?.let { ShowErrorMessage(it) }
            is Resource.InternetError -> ShowErrorMessage(stringResource(R.string.please_check_your_internet_connection))
            is Resource.IOError -> bookDetailInfo.value.message?.let { ShowErrorMessage(it) }
        }
    }
}

@Composable
fun ShowBookDetailScreen(bookDetailModel: BookDetailModel) {

        Column(modifier = Modifier.padding(10.dp)) {
            DisplayBookImageFromUrl(bookDetailModel.thumbnail,
                Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                stringResource(R.string.book_image)
            )


            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = bookDetailModel.bookTitle,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = bookDetailModel.bookSubtitle,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Written By: ${bookDetailModel.bookAuthors}",
                style = MaterialTheme.typography.bodyMedium
            )


            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Published By: ${bookDetailModel.publisher}",
                style = MaterialTheme.typography.bodyMedium,
            )


            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Published On: ${bookDetailModel.publishDate}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
