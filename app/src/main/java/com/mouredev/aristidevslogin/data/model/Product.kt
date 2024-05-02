package com.mouredev.aristidevslogin.data.model

import java.util.Date


open class Product(
    open val productId: Long?,
    open val title: String,
    open val description: String,
    open val stock: Int,
    open val language: String?,
    open val productType: ProductType,
    open val price: Double,
    open val rating: Double?,
    open val url: String,
    open val genre: String,
    open val releaseDate: Date,
    open val available: Boolean
)