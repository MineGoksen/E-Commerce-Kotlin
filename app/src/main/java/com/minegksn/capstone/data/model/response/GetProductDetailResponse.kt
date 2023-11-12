package com.minegksn.capstone.data.model.response

import com.minegksn.capstone.data.model.response.Product

data class GetProductDetailResponse(
    val product: Product?,
    val status: Int?,
    val message: String?
)