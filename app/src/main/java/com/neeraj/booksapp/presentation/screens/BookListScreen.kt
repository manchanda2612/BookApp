package com.neeraj.booksapp.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.neeraj.booksapp.domain.model.BooksListModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.neeraj.booksapp.R
import com.neeraj.booksapp.common.Resource
import com.neeraj.booksapp.presentation.common.DisplayBookImageFromUrl
import com.neeraj.booksapp.presentation.common.ShowProgressBar
import com.neeraj.booksapp.presentation.common.ShowErrorMessage
import com.neeraj.booksapp.presentation.common.ShowToolbar
import com.neeraj.booksapp.presentation.route.Routes
import com.neeraj.booksapp.presentation.view_model.BookListViewModel


/**
 * @author Neeraj Manchanda
 * It is a Composable function that represents the UI for displaying book list using Jetpack Compose.
 */
@Composable
fun BookListScreen(navController: NavController) {

    val mBookViewModel: BookListViewModel = hiltViewModel()
    val mBookList = mBookViewModel.mBookListViewModel.collectAsState()



    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ShowToolbar(stringResource(R.string.book_listing))
        when (mBookList.value) {
            is Resource.Loading -> ShowProgressBar()
            is Resource.Success -> mBookList.value.data?.let { ShowBookList(it, navController) }
            is Resource.Error -> mBookList.value.message?.let { ShowErrorMessage(it) }
            is Resource.InternetError -> ShowErrorMessage(stringResource(R.string.please_check_your_internet_connection))
            is Resource.IOError -> mBookList.value.message?.let { ShowErrorMessage(it) }
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
        Card(
            modifier = Modifier
                .padding(10.dp)
                .clickable { onClick(book, navController) }
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                DisplayBookImageFromUrl(book.smallThumbnail, Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(vertical = 4.dp),
                    stringResource(R.string.book_image)
                    )
                Text(
                    text = book.bookTitle,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                if (book.bookAuthor.isNotEmpty()) {
                    Text(
                        text = "Written By: " + book.bookAuthor,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }


fun onClick(book: BooksListModel, navController: NavController) {
    navController.navigate(Routes.BookDetailScreen.route + "/${book.bookId}")
}


