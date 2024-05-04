package com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mouredev.aristidevslogin.data.model.Product

@Composable
fun ProductDetailScreen(product: Int?) {




    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        if (product != null) {
            // Display product details using product.property
            Text(text = product.toString()) // Example usage
        } else {
            // Display a message indicating no product was provided
            Text(text = "No product details available")
        }
    }


}