package com.mouredev.aristidevslogin.ui.home.screen

import android.widget.RatingBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mouredev.aristidevslogin.R
import java.time.format.TextStyle

@Composable
fun HomeScreen() {

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(200.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White)

    ) {

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .height(250.dp)
                .clip(RoundedCornerShape(22.dp)),
            painter = painterResource(id = R.drawable.hogwards),
            contentDescription = "Hogwards Legacy PS4",
            contentScale = ContentScale.Crop
        )
    }


    Spacer(modifier = Modifier.height(6.dp))

    Text(
        modifier = Modifier.padding(start = 16.dp, end = 8.dp),
        text = "Hogwarts Legacy PS4",
        color = Color.White,
        fontSize = 15.sp,
        maxLines = 1
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, bottom = 12.dp, top = 4.dp)
    ) {

        /*
        RatingBar(
            starsModifier = Modifier.size(18.dp),
            rating = 4.5
        )
*/
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = "movie.vote_average.toString().take(3)",
            color = Color.LightGray,
            fontSize = 14.sp,
            maxLines = 1,
        )
    }


    /*
        Card(
            modifier = Modifier
                .width(200.dp)
                .height(270.dp),
            // shape = CutCornerShape(20.dp)
            elevation = CardDefaults.cardElevation(10.dp),
            //border = BorderStroke(3.dp,Color.Gray)
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.hogwards),
                    contentDescription = "null"
                )
                Text(
                    text = "Title",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    text = "Hogwarts Legacy es un RPG inmersivo de acción en mundo abierto. " +
                            "Ahora puedes tomar el control de la acción y ser el centro de tu " +
                            "propia aventura en el mundo mágico.",
                    fontSize = 13.sp,
                    modifier = Modifier.padding(6.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray
                )
            }
            }
            */

}

@Preview
@Composable
fun PreviewHome() {
    HomeScreen()
}
