package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Long
    ): Response<Product>


}