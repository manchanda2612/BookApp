package com.neeraj.booksapp.domain.use_cases

import com.neeraj.booksapp.common.Resource
import com.neeraj.booksapp.domain.model.BooksListModel
import com.neeraj.booksapp.domain.respository.BookRepository
import javax.inject.Inject

/**
 * @author Neeraj Manchanda
 * This class help to encapsulate the logic for fetching book list using the bookRepository.
 * A class helps to centralize and manage the business logic of getting book list data.
 */
class GetBookListUseCase @Inject constructor (private val bookRepository: BookRepository) {

    suspend operator fun invoke() : Resource<List<BooksListModel>> = bookRepository.getBooksList()

}