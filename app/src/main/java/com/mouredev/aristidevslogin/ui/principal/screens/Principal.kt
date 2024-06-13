package com.mouredev.aristidevslogin.ui.principal.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.mouredev.aristidevslogin.R
import com.mouredev.aristidevslogin.ScreenRoutes
import com.mouredev.aristidevslogin.data.BooksRepositoryImpl
import com.mouredev.aristidevslogin.data.GamesRepositoryImpl
import com.mouredev.aristidevslogin.data.MoviesRepositoryImpl
import com.mouredev.aristidevslogin.data.OrdersRepositoryImpl
import com.mouredev.aristidevslogin.data.ProductsRepositoryImpl
import com.mouredev.aristidevslogin.data.RetrofitInstance
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.screens.BookScreen
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.screens.GameScreen
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.screens.MovieScreen
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.screens.ProductDetailScreen
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.screens.ProductScreen
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.ui.BooksViewModel
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.ui.GamesViewModel
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.ui.MoviesViewModel
import com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.ui.ProductsViewModel
import com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.contact.ContactScreen
import com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.UserManualScreen
import com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.cart.CartScreen
import com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.cart.CartViewModel
import com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.orders.OrdersScreen
import com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.orders.OrdersViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.cart.payment.GooglePayViewModel


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PrincipalScreen() {
    NavBotSheet()
}


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@RequiresApi(Build.VERSION_CODES.O)
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
    val ordersViewModel = OrdersViewModel(OrdersRepositoryImpl(RetrofitInstance.api))

    val cartViewModel = remember { CartViewModel() }

    val googlePayViewModel: GooglePayViewModel = viewModel()

    val showBottomBar = when (val route =
        navigationController.currentBackStackEntryAsState().value?.destination?.route) {
        null -> true
        else -> {
            route.startsWith(ScreenRoutes.ProductDetailScreen.route) ||
                    route in routesToHideBars.map { it.route }
        }
    }

    /*
    var badgeCount: Int = 0

    LaunchedEffect(Unit) {
        badgeCount = cartViewModel.cartItems.value.size
    }
    */

    ModalNavigationDrawer(
        modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                NavBarHeader()

                HorizontalDivider()

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
                        navigationController.navigate(ScreenRoutes.OrdersScreen.route) {

                        }
                    }
                )

                NavigationDrawerItem(
                    label = { Text(text = "Carrito") }, selected = false,
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

                        }
                    },
                    /*
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
                                    fontSize = 10.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    */
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
                        navigationController.navigate(ScreenRoutes.ContactScreen.route) {

                        }
                    }
                )

                NavigationDrawerItem(
                    label = { Text(text = "Manual de Usuario") },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Help,
                            contentDescription = "Help Icon"
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(ScreenRoutes.UserManualScreen.route) {

                        }
                    }
                )


            }
        },
    ) {

        Scaffold(
            modifier = Modifier.background(MaterialTheme.colorScheme.primary),
            topBar = {
                if (!showBottomBar) {
                    val coroutineScope = rememberCoroutineScope()
                    TopAppBar(
                        title = { },
                        navigationIcon = {
                            IconButton(
                                onClick = { coroutineScope.launch { drawerState.open() } }
                            ) {
                                Icon(Icons.Rounded.Menu, contentDescription = "MenuButton")
                            }
                        },
                        modifier = Modifier.height(40.dp)
                    )
                }

            },
            bottomBar = {
                if (!showBottomBar) {
                    BottomBar(navigationController)
                }
            }

        ) {
            NavHost(
                navController = navigationController,
                startDestination = ScreenRoutes.ProductScreen.route
            ) {
                composable(ScreenRoutes.UserManualScreen.route) { UserManualScreen() }
                composable(ScreenRoutes.OrdersScreen.route) {
                    OrdersScreen(
                        ordersViewModel,
                        productViewModel
                    )
                }
                composable(ScreenRoutes.CartScreen.route) {
                    CartScreen(
                        cartViewModel,
                        googlePayViewModel,
                        navigationController
                    )
                }
                composable(ScreenRoutes.ContactScreen.route) { ContactScreen() }

                composable(ScreenRoutes.ProductScreen.route) {
                    ProductScreen(
                        productViewModel,
                        cartViewModel,
                        navigationController
                    )
                }

                composable(ScreenRoutes.BookScreen.route) {
                    BookScreen(
                        bookViewModel,
                        cartViewModel,
                        navigationController
                    )
                }
                composable(ScreenRoutes.MovieScreen.route) {
                    MovieScreen(
                        movieViewModel,
                        cartViewModel,
                        navigationController
                    )
                }
                composable(ScreenRoutes.GameScreen.route) {
                    GameScreen(
                        gameViewModel,
                        cartViewModel,
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

                    val productId by remember { mutableStateOf(product.arguments!!.getLong("product")) }

                    LaunchedEffect(key1 = productId) {
                        productViewModel.loadProduct(productId)
                    }

                    val productState = productViewModel.product.collectAsState().value

                    if (productState?.data != null) {
                        val product = productState.data
                        ProductDetailScreen(
                            product,
                            bookViewModel,
                            movieViewModel,
                            gameViewModel,
                            cartViewModel
                        )
                    } else {
                        CircularProgressIndicator()
                    }

                }

            }


        }
    }

}

//Función para elegir en que rutas no se mostrarán ni la top bar ni la bottom bar
val routesToHideBars = listOf(
    ScreenRoutes.UserManualScreen,
    ScreenRoutes.OrdersScreen,
    ScreenRoutes.CartScreen,
    ScreenRoutes.ContactScreen,
    //ScreenRoutes.ProductDetailScreen,
)


@Composable
fun BottomBar(navigationController: NavController) {
    val selected = remember {
        mutableStateOf(Icons.Default.List)
    }

    BottomAppBar(
        modifier = Modifier
            .height(50.dp)
            .background(MaterialTheme.colorScheme.primary),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        selected.value = Icons.Default.List
                        navigationController.navigate(ScreenRoutes.ProductScreen.route) {
                            popUpTo(0)
                        }

                    }, modifier = Modifier
                        .background(
                            if (selected.value == Icons.Default.List) MaterialTheme.colorScheme.secondary else Color.Transparent
                        )
                        .size(40.dp)
                        .clip(
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Icon(
                        Icons.Default.List,
                        contentDescription = "Product Icon",
                        modifier = Modifier.size(40.dp),
                        tint = if (selected.value == Icons.Default.List) Color.White else Color(
                            204,
                            173,
                            228
                        )
                    )
                }

                IconButton(
                    onClick = {
                        selected.value = Icons.Default.Book
                        navigationController.navigate(ScreenRoutes.BookScreen.route) {
                            popUpTo(0)
                        }
                    }, modifier = Modifier
                        .background(
                            if (selected.value == Icons.Default.Book) MaterialTheme.colorScheme.secondary else Color.Transparent
                        )
                        .size(40.dp)
                        .clip(
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Icon(
                        Icons.Default.Book,
                        contentDescription = "Book Icon",
                        modifier = Modifier.size(40.dp),
                        tint = if (selected.value == Icons.Default.Book) Color.White else MaterialTheme.colorScheme.secondary
                    )
                }

                IconButton(
                    onClick = {
                        selected.value = Icons.Default.Movie
                        navigationController.navigate(ScreenRoutes.MovieScreen.route) {
                            popUpTo(0)
                        }
                    }, modifier = Modifier
                        .background(
                            if (selected.value == Icons.Default.Movie) MaterialTheme.colorScheme.secondary else Color.Transparent
                        )
                        .size(40.dp)
                        .clip(
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Icon(
                        Icons.Default.Movie,
                        contentDescription = "Movie Icon",
                        modifier = Modifier.size(40.dp),
                        tint = if (selected.value == Icons.Default.Movie) Color.White else MaterialTheme.colorScheme.secondary
                    )
                }

                IconButton(
                    onClick = {
                        selected.value = Icons.Default.VideogameAsset
                        navigationController.navigate(ScreenRoutes.GameScreen.route) {
                            popUpTo(0)
                        }
                    }, modifier = Modifier
                        .background(
                            if (selected.value == Icons.Default.VideogameAsset) MaterialTheme.colorScheme.secondary else Color.Transparent
                        )
                        .size(40.dp)
                        .clip(
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Icon(
                        Icons.Default.VideogameAsset,
                        contentDescription = "Game Icon",
                        modifier = Modifier.size(40.dp),
                        tint = if (selected.value == Icons.Default.VideogameAsset) Color.White else MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    )
}


@Composable
fun NavBarHeader() {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val email = currentUser?.email!!

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
            text = email,
            modifier = Modifier.padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

    }
}



