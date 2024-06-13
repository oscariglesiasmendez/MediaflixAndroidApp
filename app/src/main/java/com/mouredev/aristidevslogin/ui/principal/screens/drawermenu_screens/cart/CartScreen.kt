package com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.cart

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.firebase.auth.FirebaseAuth
import com.mouredev.aristidevslogin.data.RetrofitInstance
import com.mouredev.aristidevslogin.data.model.Client
import com.mouredev.aristidevslogin.data.model.Order
import com.mouredev.aristidevslogin.data.model.OrderDetail
import com.mouredev.aristidevslogin.data.model.OrderStatus
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.cart.payment.GooglePayViewModel

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    googlePayViewModel: GooglePayViewModel,
    navigationController: NavHostController
) {

    val cartItems by cartViewModel.cartItems.collectAsState(initial = emptyList())
    val orderDetails = mutableStateListOf<OrderDetail>()
    val context = LocalContext.current

    val currentUser = FirebaseAuth.getInstance().currentUser
    val scope = rememberCoroutineScope()
    val email = currentUser?.email!!

    // Hacer un call para obtener el producto

    if (cartItems.isEmpty()) {
        EmptyCartScreen(navigationController)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            Text(
                text = "Carrito",
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(cartItems) { cartItem ->
                    CartItemRow(
                        cartItem = cartItem,
                        onRemoveItem = { cartViewModel.removeFromCart(cartItem.product.productId!!) },
                        onQuantityChange = { newQuantity ->
                            if (newQuantity <= cartItem.product.stock) {
                                val updatedCartItem = cartItem.copy(quantity = newQuantity)
                                cartViewModel.updateCartItem(updatedCartItem)
                            } else {
                                Log.d(
                                    "CartItemRow",
                                    "Insufficient stock for product: ${cartItem.product.title}"
                                )
                                Toast.makeText(
                                    context,
                                    "No hay suficiente stock de ese artículo",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Subtotal:",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Text(
                        text = String.format("%.2f €", cartItems.sumOf { it.price * it.quantity }),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Button(
                    onClick = {
                        scope.launch {
                            getClientByEmail(email)?.let { client ->

                                // Limpio la lista
                                orderDetails.clear()

                                // Creo Order Details a partir de los items del cart
                                cartItems.forEach { cartItem ->
                                    orderDetails.add(
                                        OrderDetail(
                                            orderId = 0L, // Pongo id a 0 porque cuando genere el order la API asignará su id automáticamente
                                            productId = cartItem.product.productId!!,
                                            quantity = cartItem.quantity,
                                            unitPrice = cartItem.price
                                        )
                                    )
                                }

                                var order = createOrder(client.clientId, orderDetails)

                                if(order == null){

                                    return@launch
                                }

                                cartViewModel.clearCart()


                                Log.d(
                                    "Mediaflix",
                                    "Compra realizada por: $client de articulos $orderDetails"
                                )

                            }
                        }

                        if (context is Activity) {
                            googlePayViewModel.loadPaymentData(context, 1L )
                            val total = cartItems.sumOf { it.price * it.quantity }
                            val formattedTotal = String.format("%.2f", total)
                            val notification = Notification(context, "Mediaflix", "¡Pedido de $formattedTotal € realizado!")
                            notification.fireNotification()
                        }

                    },
                    modifier = Modifier.padding(8.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                    enabled = cartItems.isNotEmpty()
                ) {
                    Text(text = "Proceder al pago", color = MaterialTheme.colorScheme.primary)
                }

/*
                PayButton(
                    modifier = Modifier
                        .testTag("payButton")
                        .fillMaxWidth(),
                    onClick = onGooglePayButtonClick,
                    allowedPaymentMethods = PaymentsUtil.allowedPaymentMethods.toString()
                )

 */



                // Google Pay Button
                /*Button(
                    onClick = {
                        if (context is Activity) {
                            googlePayViewModel.loadPaymentData(context, 1L )
                        }
                    },
                    modifier = Modifier.padding(8.dp),
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    enabled = cartItems.isNotEmpty()
                ) {
                    Text(text = "Pay with Google")
                }
                */


            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
suspend fun createOrder(clientId: Long, orderDetails: List<OrderDetail>): Order? {
    val total = orderDetails.sumOf { it.unitPrice * it.quantity }
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val formattedDate = LocalDateTime.now().format(formatter)

    val order = Order(
        orderId = 0L,
        creationDate = formattedDate,
        total = total,
        paymentMethod = "Google Pay",
        status = OrderStatus.COMPLETED,
        details = orderDetails
    )
    return try {
        val response = RetrofitInstance.orderService.createOrder(clientId, order)
        if (response.isSuccessful) {
            response.body()
        } else {
            Log.d("Mediaflix", "Error al crear pedido: ${response.code()}")
            null
        }
    } catch (e: Exception) {
        Log.d("Mediaflix", "Excepción al crear pedido: ${e.message}")
        null
    }
}


suspend fun getClientByEmail(email: String): Client? {
    try {
        val response = RetrofitInstance.clientService.searchClientByEmail(email)

        if (response.isSuccessful) {
            Log.d("Mediaflix", "Cliente encontrado: ${response.body()}")
            return response.body()
        } else {
            Log.d("Mediaflix", "Error al buscar cliente: ${response.code()}")
        }
    } catch (e: Exception) {
        Log.d("Mediaflix", "Excepción al buscar cliente: ${e.message}")
    }
    return null
}


@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun EmptyCartScreen(navigationController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Tu carrito está vacío",
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        /*
        Button(
            onClick = { navigationController.navigate(ScreenRoutes.PrincipalScreen.route) },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
        ) {
            Text(text = "Volver a la tienda", color = MaterialTheme.colorScheme.primary)
        }
        */
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CartItemRow(
    cartItem: CartItem,
    onRemoveItem: (Long) -> Unit,
    onQuantityChange: (Int) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        SubcomposeAsyncImage(
            model = cartItem.product.url,
            contentDescription = cartItem.product.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 22.dp,
                        topEnd = 22.dp,
                        bottomStart = 22.dp,
                        bottomEnd = 22.dp
                    )
                )
                .size(100.dp)
                .height(120.dp)
        ) {
            when (val state = painter.state) {
                is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                is AsyncImagePainter.State.Error -> Text(text = "Error")
                else -> SubcomposeAsyncImageContent()
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(text = cartItem.product.title, fontWeight = FontWeight.Bold, modifier = Modifier.basicMarquee(), color = MaterialTheme.colorScheme.secondary)
            Text(text = String.format("%.2f €", cartItem.price * cartItem.quantity), color = MaterialTheme.colorScheme.secondary)

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        val newQuantity = cartItem.quantity - 1
                        if (newQuantity >= 1) {
                            onQuantityChange(newQuantity)
                        }
                    },
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                    enabled = cartItem.quantity > 1
                ) {
                    Text(text = "-", color = MaterialTheme.colorScheme.primary)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = cartItem.quantity.toString(),
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { onQuantityChange(cartItem.quantity + 1) },
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                    enabled = cartItem.quantity < cartItem.product.stock
                ) {
                    Text(text = "+", color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        IconButton(
            onClick = { onRemoveItem(cartItem.product.productId!!) },
            modifier = Modifier.padding(start = 16.dp),
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Remove")
        }
    }
}
