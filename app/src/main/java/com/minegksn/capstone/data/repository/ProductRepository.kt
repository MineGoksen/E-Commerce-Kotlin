package com.minegksn.capstone.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.minegksn.capstone.data.roomdb.FavoriteProduct
import com.minegksn.capstone.data.roomdb.FavoriteProductDao
import com.minegksn.capstone.data.roomdb.FavoriteProductDatabase


class ProductRepository(context: Context) {

    var isLoading = MutableLiveData<Boolean>()
    var isProductAddedBasket = MutableLiveData<Boolean>()
    var productBasketList = MutableLiveData<List<FavoriteProduct>>()

    private val productFavDAOInterface: FavoriteProductDao? =
        FavoriteProductDatabase.productFavRoomDatabase(context)?.userDao()

    fun productFav() {
        isLoading.value = true
        productFavDAOInterface?.getAll()?.let {
            productBasketList.value = it
            isLoading.value = false
        } ?: run {
            isLoading.value = false
        }
    }

    fun addProductToFav(productModel: FavoriteProduct) {
        productFavDAOInterface?.getProductNamesFav()?.let {
            isProductAddedBasket.value = if (it.contains(productModel.title).not()) {
                productFavDAOInterface.addProductFav(productModel)
                true
            } else {
                false
            }
        }
    }

    fun deleteProductFromFav(productId: Int) {
        productFavDAOInterface?.deleteProductWithId(productId)
    }

    fun clearFav() {
        productFavDAOInterface?.clearFav()
    }

}
