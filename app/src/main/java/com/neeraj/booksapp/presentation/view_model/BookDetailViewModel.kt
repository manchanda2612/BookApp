package com.neeraj.booksapp.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neeraj.booksapp.common.Resource
import com.neeraj.booksapp.domain.model.BookDetailModel
import com.neeraj.booksapp.domain.use_cases.GetBookDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Neeraj Manchanda
 * It is a ViewModel class that handles the logic for fetching book details using the GetBookDetailUseCase.
 * It uses state management approach to update the UI as the data changes.
 */
@HiltViewModel
class BookDetailViewModel @Inject constructor(private val bookDetailUseCase: GetBookDetailUseCase) : ViewModel()  {

    private val mBookDetailResponse = MutableStateFlow<Resource<BookDetailModel>>(Resource.Loading())
    val mBookDetail : StateFlow<Resource<BookDetailModel>>
        get() = mBookDetailResponse


    fun getBookDetail(bookId : String) {
        viewModelScope.launch(Dispatchers.Default) {
            mBookDetailResponse.value = bookDetailUseCase(bookId)
        }
    }

}