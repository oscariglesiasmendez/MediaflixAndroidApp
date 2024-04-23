package com.mouredev.aristidevslogin.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
fun ProductCard(
    imageUrl: String,
    title: String,
    price: Double,
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(200.dp) // You can adjust the width as needed
            .padding(8.dp)
            .clip(RoundedCornerShape(28.dp, 28.dp, 0.dp, 0.dp))
            .background(Color.White)
            .fillMaxWidth()
    ) {

        SubcomposeAsyncImage(
            model = imageUrl, //Url de la imagen en Cloudinary
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp, bottomStart = 0.dp, bottomEnd = 0.dp)),
        ) {
            when (val state = painter.state) {
                is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                is AsyncImagePainter.State.Error -> Text(text = "Error")
                else -> SubcomposeAsyncImageContent()
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp, end = 8.dp),
            text = title,
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
                    text = price.toString() + " €",
                    color = Color(35, 7, 59),
                    fontSize = 20.sp,
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
                    colors = ButtonDefaults.buttonColors(Color(35, 7, 59))
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = "Shopping Cart",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}