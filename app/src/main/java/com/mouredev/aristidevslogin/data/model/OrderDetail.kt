package com.mouredev.aristidevslogin.data.model

data class OrderDetail (
    private val orderId: Long,
    val productId: Long,
    val quantity: Int,
    val unitPrice: Double
)

data class OrderDetailImport (
    val order: Order,
    val product: Product,
    val quantity: Int,
    val unitPrice: Double
)