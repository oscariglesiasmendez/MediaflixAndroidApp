package com.mouredev.aristidevslogin.data.model

import java.util.Date

class Game(
    productId: Long?,
    title: String,
    description: String,
    stock: Int,
    language: String?,
    productType: ProductType,
    price: Double,
    rating: Double?,
    url: String,
    genre: String,
    releaseDate: Date,
    available: Boolean,
    val developer: String,
    val platform: String,
    val duration: Int,
    val urlTrailer: String
) : Product(
    productId,
    title,
    description,
    stock,
    language,
    productType,
    price,
    rating,
    url,
    genre,
    releaseDate,
    available
) {

}