package com.minegksn.capstone.data.model.response

data class ProductListUI(
    val id: Int,
    val title: String,
    val salePrice: Double,
    val price: Double,
    val imageOne: String,
    val saleState: Boolean
)