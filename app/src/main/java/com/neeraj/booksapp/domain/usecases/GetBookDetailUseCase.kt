package com.neeraj.domain.usecases


import com.neeraj.booksapp.common.Resources
import com.neeraj.booksapp.domain.model.BookDetailModel
import com.neeraj.booksapp.domain.respository.BookRepository
import javax.inject.Inject

/**
 * @author Neeraj Manchanda
 * This class help to encapsulate the logic for fetching book details using the bookRepository.
 * A class helps to centralize and manage the business logic of getting book detail data.
 */
class GetBookDetailUseCase @Inject constructor(private val bookRepository: BookRepository) {

    suspend operator fun invoke(bookId : String) : Resources<BookDetailModel> = bookRepository.getBookDetail(bookId)

}