package com.mouredev.aristidevslogin.data.model

import androidx.versionedparcelable.VersionedParcelize
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
)