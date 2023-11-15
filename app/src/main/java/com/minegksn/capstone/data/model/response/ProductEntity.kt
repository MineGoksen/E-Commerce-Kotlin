package com.minegksn.capstone.data.model.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_products")
data class ProductEntity(
    @PrimaryKey
    @ColumnInfo(name = "productId")
    val productId: Int?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "price")
    val price: Double?,

    @ColumnInfo(name = "salePrice")
    val salePrice: Double?,

    @ColumnInfo(name = "saleState")
    val saleState: Boolean?,

    @ColumnInfo(name = "imageOne")
    val imageOne: String?,


)
