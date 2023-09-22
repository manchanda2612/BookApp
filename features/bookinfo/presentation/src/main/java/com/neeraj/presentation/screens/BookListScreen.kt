package com.neeraj.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.neeraj.domain.model.BooksListModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import coil.request.CachePolicy
import coil.size.Scale
import com.neeraj.common.Resources
import com.neeraj.presentation.R
import com.neeraj.presentation.uiutils.DisplayBookImageFromUrl
import com.neeraj.presentation.uiutils.ShowProgressBar
import com.neeraj.presentation.uiutils.ShowErrorMessage
import com.neeraj.presentation.uiutils.ShowToolbar
import com.neeraj.presentation.route.Routes
import com.neeraj.presentation.uiutils.Dimens
import com.neeraj.presentation.viewmodel.BookListViewModel


/**
 * @author Neeraj Manchanda
 * It is a Composable function that represents the UI for displaying book list using Jetpack Compose.
 */
@Composable
fun BookListScreen(navController: NavController) {

    val bookViewModel: BookListViewModel = hiltViewModel()
    val bookList = bookViewModel.bookListViewModel.collectAsState()


    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ShowToolbar(stringResource(R.string.book_listing))
        when (bookList.value) {
            is Resources.Loading -> ShowProgressBar()
            is Resources.Success -> ShowBookList((bookList.value as Resources.Success<List<BooksListModel>>).data, navController)
            is Resources.Failure -> ShowErrorMessage(bookList.value.toString())
        }
    }
}


@Composable
fun ShowBookList(bookList: List<BooksListModel>, navController: NavController) {

            bookList.let {
                LazyColumn {
                    items(it) {
                        ItemCard(it, navController)
                    }
                }
        }
}

@Composable
fun ItemCard(book: BooksListModel, navController: NavController) {

    Column(modifier = Modifier.padding(Dimens.five_dp)) {
        Card(
            modifier = Modifier
                .padding(Dimens.ten_dp)
                .clickable { onClick(book, navController) }
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.padding(Dimens.ten_dp)) {
                DisplayBookImageFromUrl(
                    book.smallThumbnail,
                    Modifier.fillMaxWidth().height(Dimens.hundred_dp)
                    .padding(Dimens.four_dp),
                    R.drawable.ic_book_placeholder,
                    R.drawable.ic_book_placeholder,
                    Scale.FILL,
                    ContentScale.Fit,
                    stringResource(R.string.book_image),
                    CachePolicy.ENABLED
                )
                Text(
                    text = book.bookTitle,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(Dimens.ten_dp))
                if (book.bookAuthor.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.written_by, book.bookAuthor),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(Dimens.ten_dp))
                }
            }
        }
    }
}


private fun onClick(book: BooksListModel, navController: NavController) {
    navController.navigate(Routes.BookDetailScreen.route + "/${book.bookId}")
}


