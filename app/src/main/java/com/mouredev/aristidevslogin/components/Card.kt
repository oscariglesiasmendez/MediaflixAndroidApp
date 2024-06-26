package com.mouredev.aristidevslogin.components

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.mouredev.aristidevslogin.ScreenRoutes
import com.mouredev.aristidevslogin.data.model.Product
import com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.cart.CartItem
import com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.cart.CartViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    navigationController: NavHostController,
    cartViewModel: CartViewModel
) {

    val context = LocalContext.current

    val opacity = if (product.stock > 0 && (product.available)) 1.0f else 0.5f

    Column(
        modifier = modifier
            .wrapContentHeight()
            .width(200.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(28.dp, 28.dp, 28.dp, 28.dp))
            .background(Color.White)
            .fillMaxWidth()
            .clickable(
                onClick = {
                    navigationController.navigate(ScreenRoutes.ProductDetailScreen.route + "/${product.productId}")
                }
            )
            .alpha(opacity)
    ) {

        SubcomposeAsyncImage(
            model = product.url, // Url de la imagen en Cloudinary
            contentDescription = product.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 22.dp,
                        topEnd = 22.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                ),
        ) {
            when (val state = painter.state) {
                is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                is AsyncImagePainter.State.Error -> Text(text = "Error")
                else -> SubcomposeAsyncImageContent()
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 8.dp)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .basicMarquee(),
            text = product.title,
            color = Color(35, 7, 59),
            fontSize = 15.sp,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Row(
            Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier
                    .weight(1.25f)
                    .padding(12.dp)
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
            ) {
                Text(
                    text = String.format("%.2f €", product.price),
                    color = Color(35, 7, 59),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
            ) {
                Button(
                    enabled = product.stock > 0 && product.available, // Enable button only if stock > 0
                    onClick = {
                        // Create a CartItem object with default quantity (adjust if needed)
                        val cartItem = CartItem(product = product, quantity = 1, price = product.price)

                        // Call the addToCart function of the CartViewModel
                        cartViewModel.addToCart(cartItem)

                        Toast.makeText(
                            context, "Se ha agregado el producto al carrito", Toast.LENGTH_SHORT
                        ).show()
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = "Shopping Cart",
                        modifier = Modifier.fillMaxSize(),
                        tint = Color.White
                    )
                }
            }
        }
    }
}