package com.mouredev.aristidevslogin.data.model

import com.google.gson.annotations.SerializedName

data class ProductPage(
    @SerializedName("products") // Optional, if using JSON mapping
    val products: List<Product>,
    val total: Long,
    val skip: Int,
    val limit: Int
)