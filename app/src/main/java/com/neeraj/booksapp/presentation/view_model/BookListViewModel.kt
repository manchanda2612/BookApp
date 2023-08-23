package com.neeraj.booksapp.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neeraj.booksapp.common.Resource
import com.neeraj.booksapp.domain.model.BooksListModel
import com.neeraj.booksapp.domain.use_cases.GetBookListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
class BookListViewModel @Inject constructor(private val getBookListUseCase: GetBookListUseCase) : ViewModel() {

    private val mBookListResponse = MutableStateFlow<Resource<List<BooksListModel>>>(Resource.Loading())
    val mBookList : StateFlow<Resource<List<BooksListModel>>> get() = mBookListResponse

    init {
        getBookList()
    }


    private fun getBookList() {
        viewModelScope.launch(Dispatchers.Default){
            mBookListResponse.value = getBookListUseCase()
        }
    }

}