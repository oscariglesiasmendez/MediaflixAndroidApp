package com.mouredev.aristidevslogin.apiCalls

import android.telecom.Call
import com.mouredev.aristidevslogin.entities.ProductPage
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query


interface ProductoApi {

    @GET("http://localhost:8080/api/products")
    suspend fun getAllProducts(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Deferred<ProductPage>


}