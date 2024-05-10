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
) {
    fun copy(
        productId: Long? = this.productId,
        title: String = this.title,
        description: String = this.description,
        stock: Int = this.stock,
        language: String? = this.language,
        productType: ProductType = this.productType,
        price: Double = this.price,
        rating: Double? = this.rating,
        url: String = this.url,
        genre: String = this.genre,
        releaseDate: Date = this.releaseDate,
        available: Boolean = this.available
    ): Product {
        return Product(
            productId = productId,
            title = title,
            description = description,
            stock = stock,
            language = language,
            productType = productType,
            price = price,
            rating = rating,
            url = url,
            genre = genre,
            releaseDate = releaseDate,
            available = available
        )
    }
}
/*
val emptyProduct = Product(
    productId = null,
    title = "",
    description = "",
    stock = 0,
    language = null,
    productType = ProductType.BOOK,
    price = 0.0,
    rating = null,
    url = "",
    genre = "",
    releaseDate = Date(0),
    available = false
)*/