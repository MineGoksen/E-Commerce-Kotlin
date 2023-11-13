package com.minegksn.capstone.data.mapper

import com.minegksn.capstone.data.model.response.Product
import com.minegksn.capstone.data.model.response.ProductEntity
import com.minegksn.capstone.data.model.response.ProductListUI
import com.minegksn.capstone.data.model.response.ProductUI

fun Product.mapToProductUI() =
    ProductUI(
        id = id ?: 1,
        title = title.orEmpty(),
        price = price ?: 0.0,
        description = description.orEmpty(),
        category = category.orEmpty(),
        imageOne = imageOne.orEmpty(),
        imageTwo = imageTwo.orEmpty(),
        imageThree = imageThree.orEmpty(),
        rate = rate ?: 0.0,
        count = count ?: 0,
        saleState = saleState ?: false
    )

fun List<Product>.mapProductToProductListUI() =
    map {
        ProductListUI(
            id = it.id ?: 1,
            title = it.title.orEmpty(),
            price = it.price ?: 0.0,
            imageOne = it.imageOne.orEmpty()
        )
    }

fun ProductListUI.mapToProductEntity() =
    ProductEntity(
        productId = id,
        title = title,
        price = price,
        imageOne = imageOne
    )

fun List<ProductEntity>.mapProductEntityToProductListUI() =
    map{
        ProductListUI(
            id = it.productId ?: 1,
            title = it.title.orEmpty(),
            price = it.price ?: 0.0,
            imageOne = it.imageOne.orEmpty()
        )
    }
