package com.mouredev.aristidevslogin.data.model

import java.util.Date


class Book(
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
    val isbn: Long,
    val author: String,
    val publisher: String,
    val pageNumber: Int
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

