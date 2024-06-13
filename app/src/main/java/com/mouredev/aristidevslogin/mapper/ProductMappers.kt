package com.mouredev.aristidevslogin.mapper

import com.mouredev.aristidevslogin.data.model.Product


fun Product.toProduct(): Product {
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