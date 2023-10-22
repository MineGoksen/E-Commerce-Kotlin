package com.minegksn.capstone.data.model

data class GetProductsResponse(
    val products: List<Product>?,
    val status: Int?,
    val message: String?
)
