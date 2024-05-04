package com.mouredev.aristidevslogin.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.mouredev.aristidevslogin.ScreenRoutes
import com.mouredev.aristidevslogin.data.model.Product
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.screens.ProductDetailScreen
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.screens.ProfileScreen

@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    navigationController: NavHostController,
) {

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
                    navigationController.navigate(ScreenRoutes.ProductDetailScreen.route+"/${product.productId}")
                }
            )
    ) {

        SubcomposeAsyncImage(
            model = product.url, //Url de la imagen en Cloudinary
            contentDescription = product.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
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
                .align(Alignment.CenterHorizontally),
            text = product.title,
            color = Color(35, 7, 59),
            fontSize = 15.sp,
            maxLines = 1,
            fontWeight = FontWeight.Bold
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
                    text = product.price.toString() + " €",
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
                    onClick = { /* Handle button click */ },
                    colors = ButtonDefaults.buttonColors(Color(35, 7, 59)),
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


