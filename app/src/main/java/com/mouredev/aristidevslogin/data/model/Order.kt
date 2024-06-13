package com.mouredev.aristidevslogin.data.model

import java.time.LocalDateTime

enum class OrderStatus {
    PENDING,
    COMPLETED,
    CANCELED
}

data class Order(
    val orderId: Long,
    val creationDate: String,
    val total: Double,
    val paymentMethod: String,
    //val client: Client,
    val status: OrderStatus,
    val details: List<OrderDetail>
)

val emptyOrder = Order(
    orderId = 0L, // Or any placeholder value for ID
    creationDate = "",
    total = 0.0,
    paymentMethod = "",
//    client = Client(/* Initialize client object here */), // Optional client field
    status = OrderStatus.PENDING, // Default status can be PENDING or any other defined value
    details = emptyList()
)