package com.example.magicgathering.data.api

import android.app.Application
import com.example.magicgathering.util.MyApplicationContext
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


object RetrofitBuilder {
    private const val BASE_URL = "https://api.magicthegathering.io/v1/"
    private val httpCacheDirectory: File = File(MyApplicationContext.getContext().cacheDir, "httpCache")
    private val cache: Cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)

    private val httpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor { chain: Interceptor.Chain ->
            try {
                return@addInterceptor chain.proceed(chain.request())
            } catch (e: Exception) {
                val offlineRequest: Request = chain.request().newBuilder()
                    .header(
                        "Cache-Control", "public, only-if-cached," +
                                "max-stale=" + 60 * 60 * 24 * 185
                    )
                    .build()
                return@addInterceptor chain.proceed(offlineRequest)
            }
        }
        .build()

    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val magicService: MagicService  = getRetrofit().create(MagicService::class.java)
}