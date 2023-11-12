package com.minegksn.capstone.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minegksn.capstone.common.Resource
import com.minegksn.capstone.data.model.AddToCartRequest
import com.minegksn.capstone.data.model.response.ProductUI
import com.minegksn.capstone.data.repository.ProductRepository
import com.minegksn.capstone.ui.bag.CartState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    private var _detailState = MutableLiveData<DetailState>()
    val detailState: LiveData<DetailState> get() = _detailState

    fun getProductDetail(id: Int) = viewModelScope.launch {
        _detailState.value = DetailState.Loading

        _detailState.value = when (val result = productRepository.getProductDetail(id)) {
            is Resource.Success -> DetailState.SuccessState(result.data)
            is Resource.Fail -> DetailState.EmptyScreen(result.failMessage)
            is Resource.Error -> DetailState.ShowPopUp(result.errorMessage)
        }
    }

    fun addCart(userId: String, productId: Int) = viewModelScope.launch {
        _detailState.value = DetailState.Loading

        _detailState.value = when (val result = productRepository.addToCart(AddToCartRequest(userId, productId))) {
            is Resource.Success -> {
                // Assuming result.data is a ProductUI or can be converted to it
                val productUI = result.data as? ProductUI
                if (productUI != null) {
                    DetailState.SuccessState(productUI)
                } else {
                    // Handle the case when result.data is not a ProductUI
                    DetailState.EmptyScreen("Unexpected data type")
                }
            }

            else -> {DetailState.EmptyScreen("Unexpected data type")}
        }
    }
}

sealed interface DetailState {
    object Loading : DetailState
    data class SuccessState(val product: ProductUI) : DetailState
    data class EmptyScreen(val failMessage: String) : DetailState
    data class ShowPopUp(val errorMessage: String) : DetailState
}