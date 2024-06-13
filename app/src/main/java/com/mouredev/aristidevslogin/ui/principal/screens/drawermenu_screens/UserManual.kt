package com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mouredev.aristidevslogin.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserManualScreen() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Manual de Usuario",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Section(title = "1. Bienvenida") {
            Text("Bienvenido a nuestra tienda de libros, películas y juegos. En esta aplicación, puedes explorar y comprar una amplia variedad de productos.")
            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.drawable.welcome),
                contentDescription = "Bienvenida",
                modifier = Modifier.fillMaxWidth().size(300.dp)
            )

        }

        Section(title = "2. Login") {
            Text("Para loguearte sólo tienes que introducir tu correo electrónico y contraseña o simplemente pulsa el botón de Google y selecciona tu cuenta.")
            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.drawable.login),
                contentDescription = "Bienvenida",
                modifier = Modifier.fillMaxWidth().size(300.dp)
            )

        }

        Section(title = "3. Registro") {
            Text("Para registrarte sólo rellena los campos o regístrate con Google.")
            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.drawable.sign_up),
                contentDescription = "Bienvenida",
                modifier = Modifier.fillMaxWidth().size(300.dp)
            )

        }

        Section(title = "4. Navegación") {
            Text("Usa la barra de navegación en la parte inferior de la pantalla para moverte entre las secciones de libros, películas y juegos.")
            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.drawable.navigation_bar),
                contentDescription = "Navegación",
                modifier = Modifier.fillMaxWidth().size(300.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text("Usa el menú de navegación lateral arrastrando hacia la derecha o pulsando el icono en la parte superior izquierda")

            Image(
                painter = painterResource(id = R.drawable.menu),
                contentDescription = "Navegación",
                modifier = Modifier.fillMaxWidth().size(300.dp)
            )
        }

        Section(title = "5. Explorar Productos") {
            Text("Explora nuestra colección de productos deslizando hacia arriba o hacia abajo. Puedes filtrar los productos por categorías usando los filtros disponibles.")
            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.drawable.products),
                contentDescription = "Explorar Productos",
                modifier = Modifier.fillMaxWidth().size(300.dp)
            )
        }

        Section(title = "6. Detalles del Producto") {
            Text("Haz clic en cualquier producto para ver sus detalles, incluidas las descripciones, reseñas y el precio.")
            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.drawable.product_detail),
                contentDescription = "Detalles del Producto",
                modifier = Modifier.fillMaxWidth().size(300.dp)
            )

        }

        Section(title = "7. Añadir al Carrito") {
            Text("Añade productos a tu carrito haciendo clic en el botón 'Añadir al Carrito'. Puedes ver los productos en tu carrito accediendo a la sección de carrito desde el menú lateral.")
            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.drawable.cart),
                contentDescription = "Añadir al Carrito",
                modifier = Modifier.fillMaxWidth().size(300.dp)
            )

        }

        Section(title = "8. Realizar Pedido") {
            Text("Para realizar un pedido, ve a tu carrito y haz clic en 'Proceder al Pago'. Sigue las instrucciones para completar tu compra.")
        }

        Section(title = "9. Contacto y Soporte") {
            Text("Si necesitas ayuda, ve a la sección de contacto desde el menú lateral. Nuestro equipo de soporte está disponible para ayudarte.")
            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.drawable.contact),
                contentDescription = "Contacto y Soporte",
                modifier = Modifier.fillMaxWidth().size(300.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun Section(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        content()

        Spacer(modifier = Modifier.height(8.dp))
    }
}