package com.minegksn.capstone.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minegksn.capstone.common.Resource
import com.minegksn.capstone.data.model.response.ProductListUI
import com.minegksn.capstone.data.repository.AuthRepository
import com.minegksn.capstone.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _searchState = MutableLiveData<SearchState>()
    private val searchState: LiveData<SearchState> get() = _searchState

    fun getSearch(query: Query?) = viewModelScope.launch {
        _searchState.value = SearchState.Loading

        _searchState.value = when (val result = query?.let { productRepository.searchProduct(it) }) {
            is Resource.Success -> SearchState.SuccessState(result.data)
            is Resource.Fail -> SearchState.EmptyScreen(result.failMessage)
            is Resource.Error -> SearchState.ShowPopUp(result.errorMessage)
            else -> {SearchState.ShowPopUp(result.toString())}
        }
    }


}

sealed interface SearchState {
    object Loading : SearchState
    object GoToSignIn : SearchState
    data class SuccessState(val products: List<ProductListUI>) : SearchState
    data class EmptyScreen(val failMessage: String) : SearchState
    data class ShowPopUp(val errorMessage: String) : SearchState
}