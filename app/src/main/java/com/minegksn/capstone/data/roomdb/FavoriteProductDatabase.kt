package com.minegksn.capstone.data.roomdb

import com.minegksn.capstone.data.roomdb.FavoriteProductDao
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi

@Database([FavoriteProduct::class], version = 1)
abstract class FavoriteProductDatabase : RoomDatabase() {
    abstract fun userDao(): FavoriteProductDao
    companion object {
        @Volatile
        var instance: FavoriteProductDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun productFavRoomDatabase(context: Context): FavoriteProductDatabase? {
            synchronized(this) {

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        FavoriteProductDatabase::class.java,
                        "FavoriteProduct.db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
                return instance
            }
        }
    }
}


