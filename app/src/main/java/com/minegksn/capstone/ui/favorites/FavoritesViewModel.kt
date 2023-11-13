package com.minegksn.capstone.ui.favorites

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
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _favoritesState = MutableLiveData<FavoritesState>()
    val favoritesState: LiveData<FavoritesState> get() = _favoritesState

    fun getFavorites() = viewModelScope.launch {
        _favoritesState.value = FavoritesState.Loading

        _favoritesState.value = when (val result = productRepository.getFavorites()) {
            is Resource.Success -> FavoritesState.SuccessState(result.data)
            is Resource.Fail -> FavoritesState.EmptyScreen(result.failMessage)
            is Resource.Error -> FavoritesState.ShowPopUp(result.errorMessage)
        }
    }

    fun deleteFromFavorites(product: ProductListUI) = viewModelScope.launch {
        productRepository.deleteFromFavorites(product)
        getFavorites()
    }


    fun logOut() {
        authRepository.logOut()
        _favoritesState.value = FavoritesState.GoToSignIn
    }
}

sealed interface FavoritesState {
    object Loading : FavoritesState
    object GoToSignIn : FavoritesState
    data class SuccessState(val products: List<ProductListUI>) : FavoritesState
    data class EmptyScreen(val failMessage: String) : FavoritesState
    data class ShowPopUp(val errorMessage: String) : FavoritesState
}