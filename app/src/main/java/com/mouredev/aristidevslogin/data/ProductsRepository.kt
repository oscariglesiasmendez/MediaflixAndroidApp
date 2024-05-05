package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    suspend fun getProductsList():Flow<Result<List<Product>>>

    suspend fun getProductById(productId: Long): Flow<Result<Product>>

}