package com.neeraj.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neeraj.common.Resources
import com.neeraj.domain.model.BookDetailModel
import com.neeraj.domain.usecases.GetBookDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val bookDetailResponse = MutableStateFlow<Resources<BookDetailModel>>(
        Resources.Loading)
    val bookDetail : StateFlow<Resources<BookDetailModel>>
        get() = bookDetailResponse


    fun getBookDetail(bookId : String) {
        viewModelScope.launch {
            bookDetailResponse.value = bookDetailUseCase(bookId)
        }
    }

}