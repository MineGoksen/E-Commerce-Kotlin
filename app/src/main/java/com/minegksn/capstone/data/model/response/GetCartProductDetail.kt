package com.minegksn.capstone.data.model.response

import com.minegksn.capstone.data.model.response.Product

data class GetCartProductDetail(
    val products: List<Product>?,
    val status: Int?,
    val message: String?
)
