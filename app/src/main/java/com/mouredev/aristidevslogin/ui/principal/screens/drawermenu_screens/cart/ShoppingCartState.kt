package com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.cart

import com.mouredev.aristidevslogin.data.model.Product

data class ShoppingCartState(
    val cartItems: List<Product> = emptyList(),
    val totalQuantity: Int = 0,
    val totalPrice: Double = 0.0
)