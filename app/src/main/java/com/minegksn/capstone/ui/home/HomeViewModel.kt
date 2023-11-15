package com.minegksn.capstone.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minegksn.capstone.common.Resource
import com.minegksn.capstone.data.model.response.ProductListUI
import com.minegksn.capstone.data.model.response.ProductUI
import com.minegksn.capstone.data.repository.AuthRepository
import com.minegksn.capstone.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState> get() = _homeState

    private var _saleState = MutableLiveData<SaleState>()
    val saleState: LiveData<SaleState> get() = _saleState

    fun getProducts() = viewModelScope.launch {
        _homeState.value = HomeState.Loading

        _homeState.value = when (val result = productRepository.getProducts()) {
            is Resource.Success -> HomeState.SuccessState(result.data)
            is Resource.Fail -> HomeState.EmptyScreen(result.failMessage)
            is Resource.Error -> HomeState.ShowPopUp(result.errorMessage)
        }
    }

    fun getSaleProducts() = viewModelScope.launch {
        _saleState.value = SaleState.Loading

        _saleState.value = when (val result = productRepository.getSaleProducts()) {
            is Resource.Success -> SaleState.SuccessState(result.data)
            is Resource.Fail -> SaleState.EmptyScreen(result.failMessage)
            is Resource.Error -> SaleState.ShowPopUp(result.errorMessage)
        }
    }


    fun addToFavorites(product: ProductListUI) = viewModelScope.launch{
        productRepository.addToFavorites(product)
    }

    fun logOut() {
        authRepository.logOut()
        _homeState.value = HomeState.GoToSignIn
    }
}

sealed interface HomeState {
    object Loading : HomeState
    object GoToSignIn : HomeState
    data class SuccessState(val products: List<ProductListUI>) : HomeState
    data class EmptyScreen(val failMessage: String) : HomeState
    data class ShowPopUp(val errorMessage: String) : HomeState
}


sealed interface SaleState {
    object  Loading : SaleState
    data class SuccessState(val products: List<ProductUI>) : SaleState
    data class EmptyScreen(val failMessage : String) : SaleState
    data class ShowPopUp(val errorMessage : String) : SaleState
}