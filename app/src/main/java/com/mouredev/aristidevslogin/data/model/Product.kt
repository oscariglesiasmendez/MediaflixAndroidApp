package com.mouredev.aristidevslogin.data.model

import java.util.Date

data class Product(
    val productId: Long?,
    val title: String,
    val description: String,
    val stock: Int,
    val language: String?,
    val productType: ProductType,
    val price: Double,
    val rating: Double?,
    val url: String,
    val genre: String,
    val releaseDate: Date,
    val available: Boolean
)

