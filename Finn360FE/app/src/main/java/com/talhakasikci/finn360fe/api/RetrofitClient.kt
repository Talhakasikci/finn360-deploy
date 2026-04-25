package com.talhakasikci.finn360fe.api

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

//    private val okHttpClient = OkHttpClient.Builder()
//        .addInterceptor(loggingInterceptor)
//        .build()
//
//    val instance: AuthService by lazy {
//        val retrofit = retrofit2.Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(okHttpClient)
//            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
//            .build()
//
//        retrofit.create(AuthService::class.java)
//    }

    @Volatile
    private var retrofit: Retrofit? = null

    private fun getRetrofitInstance(context: Context): Retrofit {
        return retrofit ?: synchronized(this) {
            retrofit ?: buildRetrofit(context.applicationContext).also { retrofit = it as Retrofit }
        }
    }

    fun <T> createService(context: Context, serviceClass: Class<T>): T {
        return getRetrofitInstance(context).create(serviceClass)
    }
    private fun buildRetrofit(context: Context): Retrofit {
        // 1. AuthInterceptor'ı Context ile oluştur
        val authInterceptor = AuthInterceptor(context)

        // 2. OkHttpClient'a ekle
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        // 3. Retrofit'i kur
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}