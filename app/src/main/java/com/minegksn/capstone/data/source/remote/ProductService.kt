package com.minegksn.capstone.data.source.remote

import com.minegksn.capstone.data.model.AddToCartRequest
import com.minegksn.capstone.data.model.AddToChartResponse
import com.minegksn.capstone.data.model.ClearCartRequest
import com.minegksn.capstone.data.model.ClearCartResponse
import com.minegksn.capstone.data.model.DeleteFromCartRequest
import com.minegksn.capstone.data.model.DeleteFromCartResponse
import com.minegksn.capstone.data.model.GetCartProductDetail
import com.minegksn.capstone.data.model.GetProductDetailResponse
import com.minegksn.capstone.data.model.GetProductsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductService {

    @GET("get_products.php")
    fun getProducts(): Call<GetProductsResponse>

    @GET("get_product_detail.php")
    fun getProductDetail(
        @Query("id") id: Int
    ): Call<GetProductDetailResponse>

    @GET("get_cart_products.php")
    fun getCartProduct(
        @Query("userId") userId: String
    ): Call<GetCartProductDetail>


    @POST("add_to_cart.php")
    fun addChart(@Body request: AddToCartRequest): Call<AddToChartResponse>

    @POST("delete_from_cart.php")
    fun deleteFromCart(
        @Body request: DeleteFromCartRequest
    ): Call<DeleteFromCartResponse>

    @POST("clear_cart.php")
    fun clearCart(
        @Body request: ClearCartRequest
    ): Call<ClearCartResponse>



}