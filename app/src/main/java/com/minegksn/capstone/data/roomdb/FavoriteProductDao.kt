package com.minegksn.capstone.data.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.minegksn.capstone.data.model.Product
import com.minegksn.capstone.data.roomdb.FavoriteProduct

@Dao
interface FavoriteProductDao {
    @Query("SELECT * FROM FavoriteProduct")
    fun getAll(): List<FavoriteProduct>

    @Query("DELETE FROM FavoriteProduct")
    fun clearFav()

    @Query("DELETE FROM FavoriteProduct WHERE id = :idInput")
    fun deleteProductWithId(idInput: Int)

    @Query("SELECT title FROM FavoriteProduct")
    fun getProductNamesFav(): List<String>?

    @Insert
    fun addProductFav(productBasketRoomModel: FavoriteProduct)

}
