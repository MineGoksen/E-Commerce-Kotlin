package com.minegksn.capstone.data.model

import com.minegksn.capstone.data.model.response.Product

data class GetProductByCategoryResponse(
    val products: List<Product>?,
    val status: Int?,
    val message: String?
)
