package com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.screens

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mouredev.aristidevslogin.components.ProductCard
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.ui.BooksViewModel
import com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.cart.ShoppingCartViewModel
import kotlinx.coroutines.flow.collectLatest


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookScreen(viewModel: BooksViewModel, cartViewModel : ShoppingCartViewModel, navigationController: NavHostController) {

    val bookList = viewModel.books.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.showErrorToastChannel) {
        viewModel.showErrorToastChannel.collectLatest { show ->
            if (show) {
                Toast.makeText(
                    context, "Error cargando libros", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    if (bookList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(top = 50.dp, bottom = 50.dp)
        ) {
            items(bookList.size / 2) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProductCard(
                        product = bookList[2 * row],
                        modifier = Modifier.weight(1f),
                        navigationController = navigationController,
                        viewModel = cartViewModel
                    )
                    ProductCard(
                        product = bookList[2 * row + 1],
                        modifier = Modifier.weight(1f),
                        navigationController = navigationController,
                        viewModel = cartViewModel
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

}