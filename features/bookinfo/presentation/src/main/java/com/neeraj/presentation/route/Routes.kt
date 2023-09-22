package com.neeraj.presentation.route

import com.neeraj.presentation.constant.UiConstants

/**
 * @author Neeraj Manchanda
 * Routes sealed class defines the different screens/routes in your app.
 * Each screen is represented by an object within the sealed class.
 */
sealed class Routes(val route: String) {
    object BookListScreen : Routes(UiConstants.bookListScreen)
    object BookDetailScreen : Routes(UiConstants.bookDetailScreen)
}