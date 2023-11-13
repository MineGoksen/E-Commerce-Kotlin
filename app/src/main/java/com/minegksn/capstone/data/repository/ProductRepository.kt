package com.minegksn.capstone.data.repository

import com.minegksn.capstone.common.Resource
import com.minegksn.capstone.data.mapper.mapProductEntityToProductListUI
import com.minegksn.capstone.data.mapper.mapProductToProductListUI
import com.minegksn.capstone.data.mapper.mapToProductEntity
import com.minegksn.capstone.data.mapper.mapToProductUI
import com.minegksn.capstone.data.model.AddToCartRequest
import com.minegksn.capstone.data.model.ClearCartRequest
import com.minegksn.capstone.data.model.DeleteFromCartRequest
import com.minegksn.capstone.data.model.response.AddToChartResponse
import com.minegksn.capstone.data.model.response.ClearCartResponse
import com.minegksn.capstone.data.model.response.DeleteFromCartResponse
import com.minegksn.capstone.data.model.response.ProductEntity
import com.minegksn.capstone.data.model.response.ProductListUI
import com.minegksn.capstone.data.model.response.ProductUI
import com.minegksn.capstone.data.source.local.ProductDao
import com.minegksn.capstone.data.source.remote.ProductService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(
    private val productService: ProductService,
    private val productDao: ProductDao
) {

    suspend fun getProducts(): Resource<List<ProductListUI>> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.getProducts().body()

                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty().mapProductToProductListUI())
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun getProductDetail(id: Int): Resource<ProductUI> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.getProductDetail(id).body()

                if (response?.status == 200 && response.product != null) {
                    Resource.Success(response.product.mapToProductUI())
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun getCartProducts(userId: String): Resource<List<ProductListUI>> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.getCartProduct(userId).body()

                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty().mapProductToProductListUI())
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }


    suspend fun clearAllCart(userId: String): Resource<ClearCartResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.clearCart(ClearCartRequest(userId))
                if (response.isSuccessful) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error(response.message())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun deleteProductFromCart(productId: Int): Resource<DeleteFromCartResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.deleteFromCart(DeleteFromCartRequest(productId))
                if (response.isSuccessful) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error(response.message())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }


    suspend fun addToCart(request: AddToCartRequest): Resource<AddToChartResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.addChart(request)
                if (response.isSuccessful) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error(response.message())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun addToFavorites(productUI: ProductListUI){
        productDao.addProduct(productUI.mapToProductEntity())
    }

    suspend fun deleteFromFavorites(productUI: ProductListUI){
        productDao.deleteProduct(productUI.mapToProductEntity())

    }
    suspend fun getFavorites(): Resource<List<ProductListUI>> =
        withContext(Dispatchers.IO) {
            try{
                val products = productDao.getProducts()
                if(products.isEmpty()){
                    Resource.Fail("Products not found")
                }else {
                   Resource.Success(products.mapProductEntityToProductListUI())
                }
            } catch(e: Exception){
                Resource.Error(e.message.orEmpty())
            }
        }
}