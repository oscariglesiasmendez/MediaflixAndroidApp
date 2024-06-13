package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.Order
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {

    suspend fun getAllOrdersByClient(clientId: Long): Flow<Result<List<Order>>>

}