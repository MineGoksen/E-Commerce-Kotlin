package com.minegksn.capstone

import android.app.Application
import com.minegksn.capstone.data.source.remote.ProductService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application()
