package com.minegksn.capstone.di

import com.minegksn.capstone.data.repository.ProductRepository
import com.minegksn.capstone.data.source.local.ProductDao
import com.minegksn.capstone.data.source.remote.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductsRepository(
        productService: ProductService,
        productDao: ProductDao
        ) = ProductRepository(productService, productDao)
}