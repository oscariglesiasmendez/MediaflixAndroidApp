package com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.cart

import androidx.lifecycle.ViewModel
import com.mouredev.aristidevslogin.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CartItem(
    val product: Product,
    val quantity: Int,
    val price: Double
)


class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    // Función para añadir al carrito
    fun addToCart(cartItem: CartItem) {
        // Compruebo si el producto está en el carrito
        val existingCartItem = _cartItems.value.find { it.product.productId == cartItem.product.productId }

        if (existingCartItem != null) {
            // Si ya está el producto en el carrito lo que hago es incrementar la cantidad y el precio
            val updatedCartItems = _cartItems.value.map {
                if (it.product.productId == cartItem.product.productId) {
                    it.copy(
                        quantity = it.quantity + cartItem.quantity,
                        price = it.price + cartItem.price
                    )
                } else {
                    it
                }
            }
            _cartItems.value = updatedCartItems
        } else {
            // Si el producto no está ya en el carrito lo añado
            _cartItems.value = _cartItems.value + cartItem
        }
    }

    // Función para quitar un item del carrito
    fun removeFromCart(productId: Long) {
        _cartItems.value = _cartItems.value.filter { it.product.productId != productId }
    }

    // Vaciar el carrito
    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun updateCartItem(updatedCartItem: CartItem) {
        // Update the existing cartItem in the cartItems list
        val updatedCartItems = cartItems.value.map {
            if (it.product.productId == updatedCartItem.product.productId) {
                updatedCartItem
            } else {
                it
            }
        }

        // Emit the updated cartItems list to the state flow
        _cartItems.value = updatedCartItems
    }
}

