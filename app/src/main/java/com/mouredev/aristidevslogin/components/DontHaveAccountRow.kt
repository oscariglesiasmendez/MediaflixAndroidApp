package com.mouredev.aristidevslogin.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mouredev.aristidevslogin.ui.theme.AlegreyaSansFontFamily


@Composable
fun DontHaveAccountRow(
    onSignupTap: () -> Unit = {},
) {
    Row(
        modifier = Modifier.padding(top=12.dp)
    ){
        Text("Todavía no tienes cuenta? ",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = AlegreyaSansFontFamily,
                color = Color.White
            )
        )

        Text("Registrate",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = AlegreyaSansFontFamily,
                fontWeight = FontWeight(800),
                color = Color(35, 7, 59)
            ),
            modifier = Modifier.clickable {
                onSignupTap()
            }
        )


    }
}