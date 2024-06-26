package com.mouredev.aristidevslogin.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mouredev.aristidevslogin.ui.theme.AlegreyaFontFamily
import com.mouredev.aristidevslogin.R
import com.mouredev.aristidevslogin.components.CButton
import com.mouredev.aristidevslogin.components.DontHaveAccountRow
import com.mouredev.aristidevslogin.ui.theme.AlegreyaSansFontFamily


@Composable
fun WelcomeScreen(
    navController: NavController
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .width(320.dp)
                    .height(240.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                "BIENVENIDO",
                fontSize = 32.sp,
                fontFamily = AlegreyaFontFamily,
                fontWeight = FontWeight(700),
                color = Color.White
            )

            Text(
                "La mejor selección de entretenimiento para ti.\n",
                textAlign = TextAlign.Center,
                fontFamily = AlegreyaSansFontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight(500),
                color = Color.White
            )

            Spacer(modifier = Modifier.weight(1f))


            CButton(text = "Inicia sesión con Email",
                onClick = { navController.navigate("Login") }
            )

            DontHaveAccountRow(
                onSignupTap = { navController.navigate("SignUp") }
            )

            Spacer(modifier = Modifier.height(52.dp))

        }
    }

}