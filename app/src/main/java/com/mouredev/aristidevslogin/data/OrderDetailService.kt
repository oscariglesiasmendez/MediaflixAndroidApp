package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.OrderDetail
import com.mouredev.aristidevslogin.data.model.OrderDetailImport
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface OrderDetailService {
    @GET("/api/order-details/{orderId}")
    suspend fun getOrderDetailsByOrderId(@Path("orderId") orderId: Long): Response<List<OrderDetailImport>>
}