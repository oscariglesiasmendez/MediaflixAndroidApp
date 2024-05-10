package com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.cart

import androidx.lifecycle.ViewModel
import com.mouredev.aristidevslogin.data.model.Book
import com.mouredev.aristidevslogin.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map


class ShoppingCartViewModel : ViewModel() {

    private val _shoppingCartState = MutableStateFlow<ShoppingCartState>(ShoppingCartState())

    val cartItemsFlow: Flow<List<Product>> = _shoppingCartState.map { it.cartItems } // Read-only flow of cart items

    fun addProductToCart(product: Product, quantity: Int = 1) {
        if (!product.available) {
            // Handle unavailable product (e.g., Toast message)
            return
        }
        val updatedCartItems = _shoppingCartState.value.cartItems.toMutableList()
        val existingItemIndex = updatedCartItems.indexOfFirst { it.productId == product.productId }

        if (existingItemIndex != -1) {
            val updatedProduct = updatedCartItems[existingItemIndex].copy(stock = product.stock - quantity)
            updatedCartItems[existingItemIndex] = updatedProduct
        } else {
            val updatedProduct = product.copy(stock = product.stock - quantity)
            updatedCartItems.add(updatedProduct)
        }

        val updatedTotalQuantity = updatedCartItems.size
        val updatedTotalPrice = calculateTotalPrice(updatedCartItems)
        _shoppingCartState.value = _shoppingCartState.value.copy( // Update state using copy
            cartItems = updatedCartItems,
            totalQuantity = updatedTotalQuantity,
            totalPrice = updatedTotalPrice
        )
    }

    // Function to calculate total price based on cart items
    fun calculateTotalPrice(cartItems: List<Product>): Double {
        // Implement logic to calculate total price based on product price and quantity
        var totalPrice = 0.0
        cartItems.forEach { totalPrice += it.price * it.stock }
        return totalPrice
    }

    fun onAddToCartEvent(event: AddToCartEvent) {
        addProductToCart(event.product, event.quantity)
    }
}



/*
class ShoppingCartViewModel : ViewModel() {

    private var _shoppingCartState = ShoppingCartState() // Private mutable ShoppingCartState

    val cartItemsFlow: List<Product> = _shoppingCartState.cartItems

    fun addProductToCart(product: Product, quantity: Int = 1) {
        if (!product.available) {
            // Handle unavailable product (e.g., Toast message)
            return
        }
        val updatedCartItems = _shoppingCartState.cartItems.toMutableList()
        val existingItemIndex = updatedCartItems.indexOfFirst { it.productId == product.productId }

        if (existingItemIndex != -1) {
            val updatedProduct = updatedCartItems[existingItemIndex].copy(stock = product.stock - quantity)
            updatedCartItems[existingItemIndex] = updatedProduct
        } else {
            val updatedProduct = product.copy(stock = product.stock - quantity)
            updatedCartItems.add(updatedProduct)
        }

        val updatedTotalQuantity = updatedCartItems.size
        val updatedTotalPrice = calculateTotalPrice(updatedCartItems)
        _shoppingCartState = _shoppingCartState.copy( // Update state using copy
            cartItems = updatedCartItems,
            totalQuantity = updatedTotalQuantity,
            totalPrice = updatedTotalPrice
        )
    }

    // Function to calculate total price based on cart items
    private fun calculateTotalPrice(cartItems: List<Product>): Double {
        // Implement logic to calculate total price based on product price and quantity
        var totalPrice = 0.0
        cartItems.forEach { totalPrice += it.price * it.stock }
        return totalPrice
    }

    fun onAddToCartEvent(event: AddToCartEvent) {
        addProductToCart(event.product, event.quantity)
    }
}
 */

