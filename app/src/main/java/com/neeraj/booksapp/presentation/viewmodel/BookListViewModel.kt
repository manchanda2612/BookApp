package com.neeraj.booksapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neeraj.booksapp.common.Resources
import com.neeraj.booksapp.domain.model.BooksListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @author Neeraj Manchanda
 * It is a ViewModel that manages the logic for fetching a list of books using the GetBookListUseCase.
 * It uses state management approach to update the UI as the data changes.
 */
@HiltViewModel
class BookListViewModel @Inject constructor(private val getBookListUseCase: com.neeraj.domain.usecases.GetBookListUseCase) : ViewModel() {

    private val bookListResponse = MutableStateFlow<Resources<List<BooksListModel>>>(
        Resources.Loading)
    val bookListViewModel : StateFlow<Resources<List<BooksListModel>>> get() = bookListResponse

    init {
        getBookList()
    }


    private fun getBookList() {
        viewModelScope.launch {
            bookListResponse.value = getBookListUseCase()
        }
    }

}