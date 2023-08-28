package com.neeraj.booksapp.test_util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neeraj.booksapp.common.Resource
import com.neeraj.booksapp.domain.model.BooksListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Files
import java.nio.file.Paths

private suspend fun readJSONFromResource(resourceName: String): String {
    return  String(withContext(Dispatchers.IO) {
        Files.readAllBytes(Paths.get(resourceName))
    })
}

suspend fun parseJSONToBookList(jsonString: String): Resource<List<BooksListModel>> {
    return withContext(Dispatchers.Default) {
        try {
            val gson = Gson()
            val listType = object : TypeToken<List<BooksListModel>>() {}.type
            val bookList = gson.fromJson<List<BooksListModel>>(jsonString, listType)
            Resource.Success(bookList)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred while parsing JSON")
        }
    }
}