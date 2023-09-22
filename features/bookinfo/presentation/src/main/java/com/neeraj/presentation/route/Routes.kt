package com.neeraj.presentation.route

/**
 * @author Neeraj Manchanda
 * Routes sealed class defines the different screens/routes in your app.
 * Each screen is represented by an object within the sealed class.
 */
sealed class Routes(val route: String) {
    object BookListScreen : Routes("book_list_screen")
    object BookDetailScreen : Routes("book_detail_screen")
}