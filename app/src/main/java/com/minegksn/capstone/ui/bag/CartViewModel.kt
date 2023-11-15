package com.minegksn.capstone.ui.bag

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
class CartViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _cartState = MutableLiveData<CartState>()
    val cartState: LiveData<CartState> get() = _cartState

    private val _totalPriceAmount = MutableLiveData(0.0)
    val totalPriceAmount: LiveData<Double> = _totalPriceAmount

    fun getCartProducts() = viewModelScope.launch {
        _cartState.value = CartState.Loading

        _cartState.value = when (val result = productRepository.getCartProducts(authRepository.getUserId())) {
            is Resource.Success -> {
                _totalPriceAmount.value = result.data.sumOf{
                    if(it.saleState) it.salePrice
                    else it.price
                }
                CartState.SuccessState(result.data)
            }
            is Resource.Fail -> CartState.EmptyScreen(result.failMessage)
            is Resource.Error -> CartState.ShowPopUp(result.errorMessage)
        }

    }

    fun clearAllCart(userId: String) = viewModelScope.launch {
        _cartState.value = CartState.Loading

        _cartState.value = when (val result = productRepository.clearAllCart(authRepository.getUserId())) {
            is Resource.Success -> {
                resetTotalAmount()
                CartState.SuccessState(emptyList())
            } // Assuming SuccessState should show an empty list
            is Resource.Fail -> CartState.EmptyScreen(result.failMessage)
            is Resource.Error -> CartState.ShowPopUp(result.errorMessage)
        }
    }

    fun deleteProductFromCart(productId: Int) = viewModelScope.launch {
        _cartState.value = CartState.Loading
        _cartState.value = when (val result = productRepository.deleteProductFromCart(authRepository.getUserId(),productId)) {
            is Resource.Success -> {
                getCartProducts()
                resetTotalAmount()
                CartState.ShowPopUp(result.data.message.toString())

            }
            is Resource.Fail -> CartState.EmptyScreen(result.failMessage)
            is Resource.Error -> CartState.ShowPopUp(result.errorMessage)
        }
    }

    private fun resetTotalAmount() {
        _totalPriceAmount.value = 0.0
    }
}

sealed interface CartState {
    object Loading : CartState
    data class SuccessState(val products: List<ProductListUI>) : CartState
    data class EmptyScreen(val failMessage: String) : CartState
    data class ShowPopUp(val errorMessage: String) : CartState
}