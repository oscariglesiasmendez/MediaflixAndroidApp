package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.Client
import com.mouredev.aristidevslogin.data.model.Order
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OrderService {

    @POST("orders/{clientId}")
    suspend fun createOrder(@Path("clientId") clientId:Long, @Body order: Order): Response<Order>

}