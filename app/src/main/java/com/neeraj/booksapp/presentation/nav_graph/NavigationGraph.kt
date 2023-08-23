package com.neeraj.booksapp.presentation.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.neeraj.booksapp.presentation.screens.BookListScreen
import com.neeraj.booksapp.presentation.route.Routes
import com.neeraj.booksapp.presentation.screens.BookDetailScreen


/**
 * @author Neeraj Manchanda
 * This function define a navigation graph using Navigation component.
 * It sets up navigation between two destinations: a book list screen and a book detail screen.
 */
@Composable
fun NavigationGraph() {

    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = Routes.BookListScreen.route) {

        composable(Routes.BookListScreen.route) {
            BookListScreen(navController)
        }

        composable(Routes.BookDetailScreen.route + "/{bookId}") { navBackStackEntry ->
            val bookId = navBackStackEntry.arguments?.getString("bookId")
            bookId?.let { BookDetailScreen(it) }
        }
    }
}