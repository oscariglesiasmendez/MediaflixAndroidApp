package com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.orders

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.firebase.auth.FirebaseAuth
import com.mouredev.aristidevslogin.data.model.Order
import com.mouredev.aristidevslogin.data.model.OrderDetail
import com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.cart.getClientByEmail
import com.mouredev.aristidevslogin.data.Result
import com.mouredev.aristidevslogin.data.RetrofitInstance
import com.mouredev.aristidevslogin.data.model.OrderDetailImport
import com.mouredev.aristidevslogin.data.model.Product
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.ui.ProductsViewModel
import java.text.SimpleDateFormat
import java.util.Locale


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun OrdersScreen(viewModel: OrdersViewModel, productsViewModel: ProductsViewModel) {

    val currentUser = FirebaseAuth.getInstance().currentUser
    val scope = rememberCoroutineScope()
    val email = currentUser?.email!!

    val ordersResult by viewModel.orders.collectAsState(initial = null)

    LaunchedEffect(email) {
        getClientByEmail(email)?.let { client ->
            viewModel.getOrdersByClient(client)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        when (val result = ordersResult) {
            is Result.Success -> {
                val sortedOrders = result.data?.sortedByDescending { it.orderId } ?: emptyList()
                if (sortedOrders.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Todavía no has hecho ningún pedido")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(sortedOrders) { order ->
                            OrderCard(order = order, productsViewModel)
                        }
                    }
                }
            }
            is Result.Error -> {
                // Muestra un mensaje de error
                Text(text = "Error: ${result.message}", modifier = Modifier.padding(16.dp))
            }
            null -> Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }
    }

}


@Composable
fun OrderCard(order: Order, productsViewModel: ProductsViewModel) {

    var expanded by remember { mutableStateOf(false) }
    var orderDetails by remember { mutableStateOf<List<OrderDetailImport>>(emptyList()) }
    val formattedDate = remember { formatDate(order.creationDate) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { expanded = !expanded }
            .animateContentSize(animationSpec = tween(300)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Nº Pedido: ${order.orderId}", style = MaterialTheme.typography.titleLarge)
            Text(text = "Fecha: $formattedDate", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Total: %.2f".format(Locale.US, order.total), style = MaterialTheme.typography.bodyMedium)
            Text(text = "Método de pago: ${order.paymentMethod}", style = MaterialTheme.typography.bodyMedium)

            LaunchedEffect(Unit) {
                try {
                    val response = RetrofitInstance.orderDetailService.getOrderDetailsByOrderId(order.orderId)
                    if (response.isSuccessful) {
                        orderDetails = response.body() as List<OrderDetailImport>
                    } else {

                    }
                } catch (e: Exception) {

                }
            }


            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))

                orderDetails.forEach { detail ->
                    OrderDetailCard(orderDetail = detail, detail.product.productId!!)
                }

            }
        }
    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun OrderDetailCard(orderDetail: OrderDetailImport, productId : Long) {
    var product by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitInstance.productService.getProductById(productId)
            if (response.isSuccessful) {
                product = response.body()
            } else {

            }
        } catch (e: Exception) {

        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(160.dp)
            ) {
                val posterImageState = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product?.url)
                        .size(Size.ORIGINAL)
                        .build()
                ).state

                if (posterImageState is AsyncImagePainter.State.Error) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(70.dp),
                            imageVector = Icons.Rounded.ImageNotSupported,
                            contentDescription = product?.title
                        )
                    }
                }

                if (posterImageState is AsyncImagePainter.State.Success) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                        painter = posterImageState.painter,
                        contentDescription = product?.title,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "${product?.title}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Cantidad: ${orderDetail.quantity}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Precio Unidad: %.2f".format(Locale.US, orderDetail.unitPrice) + " €", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Total: %.2f".format(Locale.US, orderDetail.unitPrice * orderDetail.quantity) + " €", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

fun formatDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    val date = inputFormat.parse(dateString)
    return outputFormat.format(date)
}