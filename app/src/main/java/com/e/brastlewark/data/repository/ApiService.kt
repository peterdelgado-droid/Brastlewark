package com.e.brastlewark.data.repository

import com.e.brastlewark.domain.Brastlewark
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("data.json")
    fun getList(
    ): Call<Brastlewark>

    companion object Factory {
        private const val baseUrl = "https://raw.githubusercontent.com/rrafols/mobile_test/master/"
        private fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        fun getOkHttpClient() = OkHttpClient().newBuilder()
                .addInterceptor { chain ->
                    val newUrl = chain.request().url()
                            .newBuilder()
                            .build()

                    val newRequest = chain.request()
                            .newBuilder()
                            .url(newUrl)
                            .build()
                    chain.proceed(newRequest)
                }
                .build()

        fun <S> createService(serviceClass: Class<S>): S {
            return getRetrofitInstance().create(serviceClass)
        }
    }
}