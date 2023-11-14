package com.minegksn.capstone.data.source.remote

import com.minegksn.capstone.data.model.AddToCartRequest
import com.minegksn.capstone.data.model.response.AddToChartResponse
import com.minegksn.capstone.data.model.ClearCartRequest
import com.minegksn.capstone.data.model.response.ClearCartResponse
import com.minegksn.capstone.data.model.DeleteFromCartRequest
import com.minegksn.capstone.data.model.response.DeleteFromCartResponse
import com.minegksn.capstone.data.model.response.GetCartProductDetail
import com.minegksn.capstone.data.model.response.GetCategoriesResponse
import com.minegksn.capstone.data.model.GetProductByCategoryResponse
import com.minegksn.capstone.data.model.response.GetProductDetailResponse
import com.minegksn.capstone.data.model.response.GetProductsResponse
import com.minegksn.capstone.data.model.GetSaleProducts
import com.minegksn.capstone.data.model.SearchProduct
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductService {

    @GET("get_products.php")
    suspend fun getProducts(): Response<GetProductsResponse>

    @GET("get_product_detail.php")
    suspend fun getProductDetail(
        @Query("id") id: Int
    ): Response<GetProductDetailResponse>

    @GET("get_cart_products.php")
    suspend fun getCartProduct(
        @Query("userId") userId: String
    ): Response<GetCartProductDetail>


    @POST("add_to_cart.php")
    suspend fun addChart(@Body request: AddToCartRequest): Response<AddToChartResponse>

    @POST("delete_from_cart.php")
    suspend fun deleteFromCart(
        @Body request: DeleteFromCartRequest
    ): Response<DeleteFromCartResponse>

    @POST("clear_cart.php")
    suspend fun clearCart(
        @Body request: ClearCartRequest
    ): Response<ClearCartResponse>

    @GET("get_products_by_category.php")
    suspend fun getByCategory(
        @Query("category") category: String
    ): Response<GetProductByCategoryResponse>

    @GET("get_sale_products.php")
    suspend fun getSale(): Response<GetSaleProducts>

    @GET("search_product.php")
    suspend fun searchProduct(
        @Query("query") query: Query
    ): Response<SearchProduct>

    @GET("get_categories.php")
    suspend fun getCategories(): Response<GetCategoriesResponse>



}