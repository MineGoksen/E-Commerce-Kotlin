package com.minegksn.capstone.ui.fav


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minegksn.capstone.data.roomdb.FavoriteProduct
import com.minegksn.capstone.data.repository.ProductRepository

class FavViewModel(context: Context) : ViewModel() {

    val productRepo = ProductRepository(context)
    var _productBasket = MutableLiveData<List<FavoriteProduct>>()

    val productBasket: LiveData<List<FavoriteProduct>>
        get() = _productBasket

    var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        _isLoading = productRepo.isLoading
        getProductFav()
    }

    fun getProductFav() {
        productRepo.productFav()
        _productBasket= productRepo.productBasketList
        _isLoading = productRepo.isLoading
    }

    fun deleteProductFromBasket(productId: Int) {
        productRepo.deleteProductFromFav(productId)
        getProductFav()
    }

    fun insertFavoriteProduct(favoriteProduct: FavoriteProduct) {
        productRepo.addProductToFav(favoriteProduct)
        getProductFav()
    }



}
