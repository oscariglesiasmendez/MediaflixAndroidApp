package com.mouredev.aristidevslogin.ui.home.screen


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mouredev.aristidevslogin.components.ProductCard
import com.mouredev.aristidevslogin.ui.home.ui.ProductsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(viewModel: ProductsViewModel) {





    Surface(
        color = Color(204, 173, 228),
        modifier = Modifier.fillMaxSize()
    ) {

        //Estado inicial del menú lateral
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        ModalNavigationDrawer(
            drawerContent = {
                //Contenido del menú (items)
                ModalDrawerSheet {
                }
            },
            drawerState = drawerState
        ) {
            Scaffold() {
                TopAppBar(
                    title = {
                        Text(text = "Mediaflix App")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            }
        }


        val productList = viewModel.products.collectAsState().value
        val context = LocalContext.current
        println(productList)
        LaunchedEffect(key1 = viewModel.showErrorToastChannel) {
            viewModel.showErrorToastChannel.collectLatest { show ->
                if (show) {
                    Toast.makeText(
                        context, "Error cargando productos", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


        if (productList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(productList.size / 2) { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProductCard(
                            product = productList[2 * row],
                            modifier = Modifier.weight(1f)
                        )
                        ProductCard(
                            product = productList[2 * row + 1],
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


/*
@Composable
fun Product(product: Product, modifier: Modifier = Modifier) {
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(product.url)
            .size(Size.ORIGINAL).build()
    ).state

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .height(300.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {

        if (imageState is AsyncImagePainter.State.Error) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (imageState is AsyncImagePainter.State.Success) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                painter = imageState.painter,
                contentDescription = product.title,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "${product.title} -- Price: ${product.price}$",
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = product.description,
            fontSize = 13.sp,
        )

    }
}
*/
