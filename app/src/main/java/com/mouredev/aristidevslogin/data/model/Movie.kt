package com.mouredev.aristidevslogin.data.model

import java.util.Date

class Movie(
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
    val director: Int,
    val duration: Int,
    val studio: String,
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