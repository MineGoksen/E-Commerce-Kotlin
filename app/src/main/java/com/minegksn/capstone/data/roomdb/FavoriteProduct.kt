package com.minegksn.capstone.data.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteProduct(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "price") val price: Double?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "category") val category: String?,
    @ColumnInfo(name = "imageOne") val imageOne: String?,
    @ColumnInfo(name = "imageTwo") val imageTwo: String?,
    @ColumnInfo(name = "imageThree") val imageThree: String?,
    @ColumnInfo(name = "rate") val rate: Double?,
    @ColumnInfo(name = "count") val count: Int?,
    @ColumnInfo(name = "saleState") val saleState: Boolean?
)

