package com.minegksn.capstone.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.minegksn.capstone.data.model.response.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductRoomDB : RoomDatabase() {

    abstract fun productDao(): ProductDao
}