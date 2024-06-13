package com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.mouredev.aristidevslogin.components.ExpandableText
import com.mouredev.aristidevslogin.components.HyperlinkText
import com.mouredev.aristidevslogin.components.RatingBar
import com.mouredev.aristidevslogin.data.model.Book
import com.mouredev.aristidevslogin.data.model.Game
import com.mouredev.aristidevslogin.data.model.Movie
import com.mouredev.aristidevslogin.data.model.Product
import com.mouredev.aristidevslogin.data.model.ProductType
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.ui.BooksViewModel
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.ui.GamesViewModel
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.ui.MoviesViewModel
import com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.cart.CartItem
import com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.cart.CartViewModel
import java.text.SimpleDateFormat


@Composable
fun ProductDetailScreen(
    product: Product,
    bookViewModel: BooksViewModel,
    movieViewModel: MoviesViewModel,
    gameViewModel: GamesViewModel,
    cartViewModel: CartViewModel
) {

    val productId = product.productId!!

    //Tuve que hacer esto porque si no con el recompose se llamaba como en bucle a la api
    LaunchedEffect(key1 = productId) {
        when (product.productType) {
            ProductType.BOOK -> bookViewModel.loadBook(productId)
            ProductType.MOVIE -> movieViewModel.loadMovie(productId)
            ProductType.GAME -> gameViewModel.loadGame(productId)
        }
    }

    val bookState = bookViewModel.book.collectAsState().value
    val movieState = movieViewModel.movie.collectAsState().value
    val gameState = gameViewModel.game.collectAsState().value

    when (product.productType) {
        ProductType.BOOK -> {
            if (bookState?.data != null) {
                BookDetail(bookState.data, cartViewModel)
            } else {
                CircularProgressIndicator()
            }
        }
        ProductType.MOVIE -> {
            if (movieState?.data != null) {
                MovieDetail(movieState.data, cartViewModel)
            } else {
                CircularProgressIndicator()
            }
        }
        ProductType.GAME -> {
            if (gameState?.data != null) {
                GameDetail(gameState.data, cartViewModel)
            } else {
                CircularProgressIndicator()
            }
        }
    }
}


@SuppressLint("SimpleDateFormat")
@Composable
fun BookDetail(book: Book?, cartViewModel: CartViewModel) {

    val context = LocalContext.current

    if (book == null) {
        CircularProgressIndicator()
    } else {

        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = dateFormat.format(book.releaseDate)

        val posterImageState = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(book.url)
                .size(Size.ORIGINAL)
                .build()
        ).state

        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                //.padding(top = 75.dp, bottom = 50.dp)
                .fillMaxSize()
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(160.dp)
                            .height(240.dp)
                    ) {
                        val posterImageState = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(book.url)
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
                                    contentDescription = book.title
                                )
                            }
                        }

                        if (posterImageState is AsyncImagePainter.State.Success) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(12.dp)),
                                painter = posterImageState.painter,
                                contentDescription = book.title,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = book.title,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .padding(start = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RatingBar(
                                starsModifier = Modifier.size(18.dp),
                                rating = book.rating!!
                            )

                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = book.rating.toString().take(3),
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 14.sp,
                                maxLines = 1,
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "Idioma: " + book.language,
                            color = MaterialTheme.colorScheme.secondary
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "Fecha: $date",
                            color = MaterialTheme.colorScheme.secondary
                        )

                        Spacer(modifier = Modifier.height(45.dp))


                        if (book.stock > 0) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {

                                Button(
                                    enabled = book.stock > 0 && book.available,
                                    onClick = {
                                        val cartItem = CartItem(product = book, quantity = 1, price = book.price)
                                        cartViewModel.addToCart(cartItem)
                                        Toast.makeText(
                                            context, "Se ha agregado el producto al carrito", Toast.LENGTH_SHORT
                                        ).show()
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.secondary
                                    ),
                                ) {
                                    Text(text = "Añadir al carrito", color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        } else {
                            Text(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                text = "Producto sin stock",
                                color = Color.Red,
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                ExpandableText(
                    text = book.description,
                    minimizedMaxLines = 4,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 16.sp,
                        lineHeight = 20.sp
                    )
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 70.dp, vertical = 25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Ficha técnica",
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Autor: ", fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                        Text(text = book.author, fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "ISBN: ", fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                        Text(text = book.isbn.toString(), fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Páginas: ", fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                        Text(text = book.pageNumber.toString(), fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Género: ", fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                        Text(text = book.genre, fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }
        }
    }
}


@SuppressLint("SimpleDateFormat")
@Composable
fun MovieDetail(movie: Movie?, cartViewModel: CartViewModel) {

    val context = LocalContext.current

    if (movie == null) {
        CircularProgressIndicator()
    } else {

        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = dateFormat.format(movie.releaseDate)

        val posterImageState = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.url)
                .size(Size.ORIGINAL)
                .build()
        ).state

        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                //.padding(top = 75.dp, bottom = 50.dp)
                .fillMaxSize()
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(160.dp)
                            .height(240.dp)
                    ) {
                        val posterImageState = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(movie.url)
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
                                    contentDescription = movie.title
                                )
                            }
                        }

                        if (posterImageState is AsyncImagePainter.State.Success) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(12.dp)),
                                painter = posterImageState.painter,
                                contentDescription = movie.title,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = movie.title,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .padding(start = 16.dp)
                        ) {
                            RatingBar(
                                starsModifier = Modifier.size(18.dp),
                                rating = movie.rating!!
                            )

                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = movie.rating.toString().take(3),
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 14.sp,
                                maxLines = 1,
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "Idioma: " + movie.language,
                            color = MaterialTheme.colorScheme.secondary
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "Fecha: $date",
                            color = MaterialTheme.colorScheme.secondary
                        )

                        Spacer(modifier = Modifier.height(45.dp))

                        if (movie.stock > 0) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {

                                Button(
                                    enabled = movie.stock > 0 && movie.available,
                                    onClick = {
                                        val cartItem = CartItem(product = movie, quantity = 1, price = movie.price)
                                        cartViewModel.addToCart(cartItem)
                                        Toast.makeText(
                                            context, "Se ha agregado el producto al carrito", Toast.LENGTH_SHORT
                                        ).show()
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.secondary
                                    ),
                                ) {
                                    Text(text = "Añadir al carrito", color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        } else {
                            Text(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                text = "Producto sin stock",
                                color = Color.Red,
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                ExpandableText(
                    text = movie.description,
                    minimizedMaxLines = 4,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 16.sp,
                        lineHeight = 20.sp
                    )
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 70.dp, vertical = 25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Ficha técnica",
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Director: ", fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                        Text(text = movie.director, fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Estudio: ", fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                        Text(text = movie.studio, fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Género: ", fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                        Text(text = movie.genre, fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    if(movie.urlTrailer.isNotEmpty() || movie.urlTrailer.isNotBlank()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Tráiler: ", fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)

                            HyperlinkText(
                                text = "Ver tráiler",
                                url = movie.urlTrailer
                            )
                        }
                    }

                }
            }
        }
    }
}


@SuppressLint("SimpleDateFormat")
@Composable
fun GameDetail(game: Game?, cartViewModel: CartViewModel) {

    val context = LocalContext.current

    if (game == null) {
        CircularProgressIndicator()
    } else {

        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = dateFormat.format(game.releaseDate)

        val posterImageState = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(game.url)
                .size(Size.ORIGINAL)
                .build()
        ).state

        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                //.padding(top = 75.dp, bottom = 50.dp)
                .fillMaxSize()
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(160.dp)
                            .height(240.dp)
                    ) {
                        val posterImageState = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(game.url)
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
                                    contentDescription = game.title
                                )
                            }
                        }

                        if (posterImageState is AsyncImagePainter.State.Success) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(12.dp)),
                                painter = posterImageState.painter,
                                contentDescription = game.title,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = game.title,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .padding(start = 16.dp)
                        ) {
                            RatingBar(
                                starsModifier = Modifier.size(18.dp),
                                rating = game.rating!!
                            )

                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = game.rating.toString().take(3),
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 14.sp,
                                maxLines = 1,
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "Idioma: " + game.language,
                            color = MaterialTheme.colorScheme.secondary
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "Fecha: $date",
                            color = MaterialTheme.colorScheme.secondary
                        )

                        Spacer(modifier = Modifier.height(45.dp))

                        if (game.stock > 0) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {

                                Button(
                                    enabled = game.stock > 0 && game.available,
                                    onClick = {
                                        val cartItem = CartItem(product = game, quantity = 1, price = game.price)
                                        cartViewModel.addToCart(cartItem)
                                        Toast.makeText(
                                            context, "Se ha agregado el producto al carrito", Toast.LENGTH_SHORT
                                        ).show()
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.secondary
                                    ),
                                ) {
                                    Text(text = "Añadir al carrito", color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        } else {
                            Text(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                text = "Producto sin stock",
                                color = Color.Red,
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                ExpandableText(
                    text = game.description,
                    minimizedMaxLines = 4,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 16.sp,
                        lineHeight = 20.sp
                    )
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 70.dp, vertical = 25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Ficha técnica",
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Desarrollador: ", fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                        Text(text = game.developer, fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Plataforma: ", fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                        Text(text = game.platform, fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Género: ", fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                        Text(text = game.genre, fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    if(game.urlTrailer.isNotEmpty() || game.urlTrailer.isNotBlank()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Tráiler: ", fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)

                            HyperlinkText(
                                text = "Ver tráiler",
                                url = game.urlTrailer
                            )
                        }
                    }
                }
            }
        }
    }
}


