package com.example.yuka.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("getProduct")
    fun getProduct(@Query("barcode") barcode: String)
            : Call<ServerResponse>
}

object NetworkRequest {

    private val api = Retrofit.Builder()
        .baseUrl("https://api.formation-android.fr/")
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .build()
        .create(API::class.java)

    fun getAPI(barcode: String, callback: Callback<ServerResponse>) {
        return api.getProduct(barcode).enqueue(callback)
    }

}