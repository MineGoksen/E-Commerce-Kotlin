package com.minegksn.capstone.di

import android.content.Context
import androidx.room.Room
import com.minegksn.capstone.common.Constants.BASE_URL
import com.minegksn.capstone.data.source.remote.ProductService
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.minegksn.capstone.data.source.local.ProductRoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDBModule {

    @Singleton
    @Provides
    fun provideProductRoomDB(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ProductRoomDB::class.java, "products").build()

    @Singleton
    @Provides
    fun provideProductDao(roomDB: ProductRoomDB) = roomDB.productDao()

}