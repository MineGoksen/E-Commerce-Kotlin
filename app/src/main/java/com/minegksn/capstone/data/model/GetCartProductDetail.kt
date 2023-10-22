package com.minegksn.capstone.data.model

data class GetCartProductDetail(
    val products: List<Product>?,
    val status: Int?,
    val message: String?
)
