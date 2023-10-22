@file:Suppress("DEPRECATION")

package com.notes.taskapp.retrofit


import android.annotation.SuppressLint
import com.notes.taskapp.baseApplication.BaseApplication
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@SuppressLint("StaticFieldLeak")
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val cacheSize = (10 * 1024 * 1024).toLong()
    private val myCache = Cache(BaseApplication.mInstance.cacheDir, cacheSize)

    private var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .cache(myCache)
        .build()

    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi.Builder().run {
        add(KotlinJsonAdapterFactory())
        build()
    }

    @Provides
    @Singleton
    fun providesApiService(moshi: Moshi): ApiService = Retrofit.Builder().run {
        baseUrl(ApiService.BASE_URL)
            .client(okHttpClient)
        addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }.create(ApiService::class.java)

    private fun time(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }
}