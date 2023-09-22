package com.neeraj.presentation.testutil

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neeraj.common.Constants
import com.neeraj.common.DataError
import com.neeraj.common.Resources
import com.neeraj.domain.model.BookDetailModel
import com.neeraj.domain.model.BooksListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Files
import java.nio.file.Paths


/**
 * @author Neeraj Manchanda
 * This class contains utility functions for reading JSON from a resource file and parsing it into various data types,
 * including lists of BooksListModel and BookDetailModel. It also includes a generic function for parsing JSON into a Retrofit Response object.
 */
class TestUtils {

    companion object {
        suspend fun readJSONFromResource(resourceName: String): String {
            return String(withContext(Dispatchers.IO) {
                Files.readAllBytes(Paths.get(resourceName))
            })
        }

        suspend fun parseJSONToBookList(jsonString: String): Resources<List<BooksListModel>> {
            return withContext(Dispatchers.IO) {
                try {
                    val gson = Gson()
                    val listType = object : TypeToken<List<BooksListModel>>() {}.type
                    val bookList = gson.fromJson<List<BooksListModel>>(jsonString, listType)
                    Resources.Success(bookList)
                } catch (e: Exception) {
                    Resources.Failure(DataError(e.message ?: Constants.ErrorMessage))
                }
            }
        }

        suspend fun parseJSONToBookDetail(jsonString: String): Resources<BookDetailModel> {
            return withContext(Dispatchers.IO) {
                try {
                    val gson = Gson()
                    val type = object : TypeToken<BookDetailModel>() {}.type
                    val bookList = gson.fromJson<BookDetailModel>(jsonString, type)
                    Resources.Success(bookList)
                } catch (e: Exception) {
                    Resources.Failure(DataError(e.message ?: Constants.ErrorMessage))
                }
            }
        }
    }
}

