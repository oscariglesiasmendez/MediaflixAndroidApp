package com.mouredev.aristidevslogin.ui.home.screen

import android.widget.RatingBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.mouredev.aristidevslogin.R
import java.time.format.TextStyle

@Composable
fun HomeScreen() {

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(200.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(28.dp, 28.dp, 0.dp, 0.dp))
            .background(Color.White)
            .fillMaxWidth()
    ) {

        AsyncImage(
            model = "http://res.cloudinary.com/dulpvbj0d/image/upload/v1711809162/mediaflix/lfqj4vvhxbhcxotdtdkn.jpg",
            contentDescription = "Description of the image",
            modifier = Modifier.size(100.dp)
        )
        SubcomposeAsyncImage(
            model = "http://res.cloudinary.com/dulpvbj0d/image/upload/v1711809162/mediaflix/lfqj4vvhxbhcxotdtdkn.jpg",
            contentDescription = null,
            loading = { CircularProgressIndicator() })


        /*
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(22.dp, 22.dp, 0.dp, 0.dp)),
            painter = painterResource(id = R.drawable.hogwards),
            contentDescription = "Hogwards Legacy PS4",
            contentScale = ContentScale.Crop
        )
        */

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp, end = 8.dp),
            text = "Hogwarts Legacy PS4",
            color = Color(35, 7, 59),
            fontSize = 15.sp,
            maxLines = 1,
            fontWeight = FontWeight.Bold
        )

        Row(
            Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier // Start with the weight and padding modifiers
                    .weight(1.25f)
                    .padding(12.dp)
                    .align(Alignment.CenterVertically) // Then apply the vertical alignment modifier
                    .fillMaxWidth() // Finally, apply the fillMaxWidth modifier
            ) {
                Text(
                    text = "69.95 €",
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
                        tint = Color(204, 173, 228)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewHome() {
    HomeScreen()
}
