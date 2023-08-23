package com.neeraj.booksapp.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.neeraj.booksapp.domain.model.BooksListModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.neeraj.booksapp.R
import com.neeraj.booksapp.common.Resource
import com.neeraj.booksapp.presentation.route.Routes
import com.neeraj.booksapp.presentation.view_model.BookListViewModel


/**
 * @author Neeraj Manchanda
 * It is a Composable function that represents the UI for displaying book list using Jetpack Compose.
 */
@Composable
fun BookListScreen(navController: NavController) {

    val viewModel: BookListViewModel = hiltViewModel()
    val bookList = viewModel.mBookList.collectAsState()

    when(bookList.value) {
        is Resource.Loading -> ProgressBar()
        is Resource.Success -> bookList.value.data?.let { ShowBookList(it, navController) }
        is Resource.Error -> bookList.value.message?.let { ShowErrorMessage(it) }
        is Resource.InternetError -> ShowErrorMessage(stringResource(R.string.please_check_your_internet_connection))
        is Resource.IOError -> bookList.value.message?.let { ShowErrorMessage(it) }
    }
}

@Composable
fun ShowErrorMessage(message : String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message)
    }
}

@Composable
fun ProgressBar() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

/*@Composable
fun BookScreenToolbar() {

    Column (modifier = Modifier.fillMaxWidth()) {
        TopAppBar(title = {
            Row (verticalAlignment = Alignment.CenterVertically){
                Text(text = "BookList", color = Color.White, fontSize = 20.dp, modifier = Modifier.fillMaxWidth().height(20.dp))
            }
        })
        Spacer(modifier = Modifier.height(10.dp))
    }
}*/


@Composable
fun ShowBookList(bookList: List<BooksListModel>, navController: NavController) {
    AppTopBar()
    Box(modifier = Modifier.fillMaxSize()) {

        Column (
            modifier = Modifier
                .fillMaxSize()
        ) {
            bookList.let {
                LazyColumn {
                    items(it) {
                        ItemCard(it, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun AppTopBar() {

    // Compose UI
   /* Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        // Use TopAppBar for the top app bar
        TopAppBar(
            title = { *//* You can place your title here *//* },

        )
    }*/
}


@Composable
fun ItemCard(book: BooksListModel, navController: NavController) {
        Card(
            modifier = Modifier
                .padding(10.dp)
                .clickable { onClick(book,navController) }
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                DisplayBookImageFromUrl(book.smallThumbnail)
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

@Composable
fun DisplayBookImageFromUrl(imageUrl: String) {

   /* AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_book_placeholder),
        error = painterResource(id = R.drawable.ic_book_placeholder),
        contentDescription = stringResource(R.string.book_image),
        contentScale = ContentScale.Crop,
        modifier = Modifier.clip(CircleShape)
    )*/

    Row(verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            placeholder = painterResource(R.drawable.ic_book_placeholder),
            error = painterResource(id = R.drawable.ic_book_placeholder),
            contentScale = ContentScale.Fit,
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(vertical = 4.dp)
        )
    }



}