package com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mouredev.aristidevslogin.components.ProductCard
import com.mouredev.aristidevslogin.data.model.Book
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.ui.BooksViewModel
import com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.cart.CartViewModel
import kotlinx.coroutines.flow.collectLatest


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookScreen(viewModel: BooksViewModel, cartViewModel : CartViewModel, navigationController: NavHostController) {

    val bookList = viewModel.books.collectAsState().value
    val context = LocalContext.current

    // State to hold search query
    val searchQuery = remember { mutableStateOf("") }
    val filteredBooks = remember { mutableStateListOf<Book>() }

    // Update filtered books when the search query changes
    LaunchedEffect(searchQuery.value, bookList) {
        filteredBooks.clear()
        filteredBooks.addAll(
            bookList.filter { it.title.contains(searchQuery.value, ignoreCase = true) ?: false }
        )
    }

    LaunchedEffect(key1 = viewModel.showErrorToastChannel) {
        viewModel.showErrorToastChannel.collectLatest { show ->
            if (show) {
                Toast.makeText(
                    context, "Error cargando libros", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        // Search bar
        TextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 20.dp, end = 20.dp),
            label = { Text("Buscar libros") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (bookList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 8.dp, bottom = 20.dp)
            ) {
                items(filteredBooks.chunked(2)) { rowBooks ->
                    if (rowBooks.size == 1) {
                        // Si sólo hay un libro
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ProductCard(
                                product = rowBooks[0],
                                modifier = Modifier.widthIn(max = 200.dp),
                                navigationController,
                                cartViewModel
                            )
                        }
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            rowBooks.forEach { book ->
                                ProductCard(
                                    product = book,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 2.dp),
                                    navigationController,
                                    cartViewModel
                                )
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    }

}