package com.neeraj.domain.usecases

import com.neeraj.common.Resources
import com.neeraj.domain.model.BooksListModel
import com.neeraj.domain.respository.BookRepository
import javax.inject.Inject

/**
 * @author Neeraj Manchanda
 * This class help to encapsulate the logic for fetching book list using the bookRepository.
 * A class helps to centralize and manage the business logic of getting book list data.
 */
class GetBookListUseCase @Inject constructor (private val bookRepository: BookRepository) {

    suspend operator fun invoke() : Resources<List<BooksListModel>> = bookRepository.getBooksList()

}