package com.mouredev.aristidevslogin.ui.principal.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mouredev.aristidevslogin.R
import com.mouredev.aristidevslogin.ScreenRoutes
import com.mouredev.aristidevslogin.data.BooksRepositoryImpl
import com.mouredev.aristidevslogin.data.GamesRepositoryImpl
import com.mouredev.aristidevslogin.data.MoviesRepositoryImpl
import com.mouredev.aristidevslogin.data.ProductsRepositoryImpl
import com.mouredev.aristidevslogin.data.RetrofitInstance
import com.mouredev.aristidevslogin.data.model.Product
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.screens.BookScreen
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.screens.GameScreen
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.screens.MovieScreen
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.screens.ProductDetailScreen
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.screens.ProductScreen
import com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.ProfileScreen
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.ui.BooksViewModel
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.ui.GamesViewModel
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.ui.MoviesViewModel
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.ui.ProductsViewModel
import com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.CartScreen
import kotlinx.coroutines.launch


@Composable
fun PrincipalScreen() {
    NavBotSheet()
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavBotSheet() {

    val navigationController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val productRepository = ProductsRepositoryImpl(RetrofitInstance.api)

    val productViewModel = ProductsViewModel(productRepository)
    val bookViewModel = BooksViewModel(BooksRepositoryImpl(RetrofitInstance.api))
    val movieViewModel = MoviesViewModel(MoviesRepositoryImpl(RetrofitInstance.api))
    val gameViewModel = GamesViewModel(GamesRepositoryImpl(RetrofitInstance.api))

    var badgeCount: Int = 0

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                NavBarHeader()

                Divider()

                NavigationDrawerItem(
                    label = { Text(text = "Perfil") },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile Icon"
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(ScreenRoutes.ProfileScreen.route) {
                            popUpTo(0)
                        }
                    }
                )

                NavigationDrawerItem(label = { Text(text = "Pedidos") }, selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AddBox,
                            contentDescription = "Order Icon"
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(ScreenRoutes.ProfileScreen.route) {
                            popUpTo(0) //No se dejan pantallas abiertas en segundo plano
                        }
                    }
                )

                NavigationDrawerItem(label = { Text(text = "Carrito") }, selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "ShoppingCart Icon"
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(ScreenRoutes.CartScreen.route) {
                            popUpTo(0) //No se dejan pantallas abiertas en segundo plano
                        }
                    },
                    badge = {
                        if (badgeCount >= 0) { // Check for non-zero badge count
                            Box(
                                modifier = Modifier
                                    .padding(top = 4.dp, end = 4.dp)
                                    .size(16.dp)
                                    .background(Color.Red, shape = CircleShape)
                            ) {
                                Text(
                                    text = badgeCount.toString(),
                                    color = Color.White,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                )

                NavigationDrawerItem(label = { Text(text = "Contacto") }, selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "Phone Icon"
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(ScreenRoutes.BookScreen.route) {
                            popUpTo(0) //No se dejan pantallas abiertas en segundo plano
                        }
                    }
                )


            }
        },
    ) {

        Scaffold(
            topBar = {
                val coroutineScope = rememberCoroutineScope()
                TopAppBar(title = { Text(text = "Mediaflix") }, navigationIcon = {
                    IconButton(
                        onClick = { coroutineScope.launch { drawerState.open() } }
                    ) {
                        Icon(Icons.Rounded.Menu, contentDescription = "MenuButton")
                    }
                })
            }, bottomBar = { BottomBar(navigationController) }

        ) {
            NavHost(
                navController = navigationController,
                startDestination = ScreenRoutes.ProductScreen.route
            ) {
                composable(ScreenRoutes.ProfileScreen.route) { ProfileScreen() }
                composable(ScreenRoutes.OrdersScreen.route) { ProfileScreen() }
                composable(ScreenRoutes.CartScreen.route) { CartScreen() }
                composable(ScreenRoutes.ContactScreen.route) {
                    BookScreen(
                        bookViewModel,
                        navigationController
                    )
                }

                composable(ScreenRoutes.ProductScreen.route) {
                    ProductScreen(
                        productViewModel,
                        navigationController
                    )
                }

                composable(ScreenRoutes.BookScreen.route) {
                    BookScreen(
                        bookViewModel,
                        navigationController
                    )
                }
                composable(ScreenRoutes.MovieScreen.route) {
                    MovieScreen(
                        movieViewModel,
                        navigationController
                    )
                }
                composable(ScreenRoutes.GameScreen.route) {
                    GameScreen(
                        gameViewModel,
                        navigationController
                    )
                }


                // https://stackoverflow.com/questions/69181995/how-to-navigate-to-detail-view-clicking-in-lazycolumn-item-with-jetpack-compose

                composable(route = ScreenRoutes.ProductDetailScreen.route + "/{product}",
                    arguments = listOf(
                        navArgument(name = "product") {
                            type = NavType.LongType
                        }
                    )
                ) { product ->

                    var productId = product.arguments!!.getLong("product")


                    /*
                    val productId = remember {  // Use remember to avoid unnecessary recompositions
                        product.arguments!!.getLong("product")
                    }
                     */
                    productViewModel.loadProduct(productId)

                    val product = productViewModel.product.collectAsState().value?.data

                    if (product != null) {
                        ProductDetailScreen(product, bookViewModel, movieViewModel, gameViewModel)
                    }

                    /*
                    var product by remember { mutableStateOf<Product?>(null) }

                    // Load product only once
                    val shouldLoadProduct = remember { mutableStateOf(true) }
                    if (shouldLoadProduct.value) {
                        LaunchedEffect(productId) { // Launch effect based on productId change
                            productViewModel.loadProduct(productId)
                            shouldLoadProduct.value = false // Prevent further loading
                        }
                    }

                    // Observe the product flow
                    val productState = productViewModel.product.collectAsState().value

                    // Update the product state when available
                    if (productState?.data != null) {
                        product = productState.data
                    }

                    // Display product details or loading/error state
                    if (product != null) {
                        ProductDetailScreen(product!!, bookViewModel, movieViewModel, gameViewModel)
                    } else {
                        // Handle loading or error state (optional)
                        // You can display a loading indicator or error message
                    }
                    */



                }

            }


        }
    }


}


@Composable
fun BottomBar(navigationController: NavController) {
    val selected = remember {
        mutableStateOf(Icons.Default.List)
    }

    BottomAppBar(
        modifier = Modifier
            .height(50.dp)
            .background(Color(35, 7, 59)),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    selected.value = Icons.Default.List
                    navigationController.navigate(ScreenRoutes.ProductScreen.route) {
                        popUpTo(0)
                    }
                }) {
                    Icon(
                        Icons.Default.List,
                        contentDescription = "Game Icon",
                        modifier = Modifier.size(40.dp),
                        tint = if (selected.value == Icons.Default.List) Color.White else Color(
                            204,
                            173,
                            228
                        )
                    )
                }

                IconButton(onClick = {
                    selected.value = Icons.Default.Book
                    navigationController.navigate(ScreenRoutes.BookScreen.route) {
                        popUpTo(0)
                    }
                }) {
                    Icon(
                        Icons.Default.Book,
                        contentDescription = "Book Icon",
                        modifier = Modifier.size(40.dp),
                        tint = if (selected.value == Icons.Default.Book) Color.White else Color(
                            204,
                            173,
                            228
                        )
                    )
                }

                IconButton(onClick = {
                    selected.value = Icons.Default.Movie
                    navigationController.navigate(ScreenRoutes.MovieScreen.route) {
                        popUpTo(0)
                    }
                }) {
                    Icon(
                        Icons.Default.Movie,
                        contentDescription = "Movie Icon",
                        modifier = Modifier.size(40.dp),
                        tint = if (selected.value == Icons.Default.Movie) Color.White else Color(
                            204,
                            173,
                            228
                        )
                    )
                }

                IconButton(onClick = {
                    selected.value = Icons.Default.VideogameAsset
                    navigationController.navigate(ScreenRoutes.GameScreen.route) {
                        popUpTo(0)
                    }
                }) {
                    Icon(
                        Icons.Default.VideogameAsset,
                        contentDescription = "Game Icon",
                        modifier = Modifier.size(40.dp),
                        tint = if (selected.value == Icons.Default.VideogameAsset) Color.White else Color(
                            204,
                            173,
                            228
                        )
                    )
                }
            }
        }
    )
}


@Composable
fun NavBarHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo), contentDescription = "logo",
            modifier = Modifier
                .size(100.dp)
                .padding(top = 10.dp)
        )
        Text(
            text = "Mediaflix",
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}
