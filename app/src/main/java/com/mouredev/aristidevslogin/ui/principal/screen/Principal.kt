package com.mouredev.aristidevslogin.ui.principal.screen

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mouredev.aristidevslogin.R
import com.mouredev.aristidevslogin.ScreenRoutes
import com.mouredev.aristidevslogin.Screens1
import com.mouredev.aristidevslogin.data.ProductsRepositoryImpl
import com.mouredev.aristidevslogin.data.RetrofitInstance
import com.mouredev.aristidevslogin.ui.home.screen.BookScreen
import com.mouredev.aristidevslogin.ui.home.screen.MovieScreen
import com.mouredev.aristidevslogin.ui.home.screen.ProductScreen
import com.mouredev.aristidevslogin.ui.home.screen.ProfileScreen
import com.mouredev.aristidevslogin.ui.home.ui.ProductsViewModel
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

    val productViewModel  = ProductsViewModel(ProductsRepositoryImpl(RetrofitInstance.api))

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
                        navigationController.navigate(ScreenRoutes.ProfileScreen().name) {
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
                        navigationController.navigate(ScreenRoutes.ProfileScreen().name) {
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
                        navigationController.navigate(ScreenRoutes.MovieScreen().name) {
                            popUpTo(0) //No se dejan pantallas abiertas en segundo plano
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
                        navigationController.navigate(ScreenRoutes.BookScreen().name) {
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
                startDestination = ScreenRoutes.ProductScreen().name
            ) {
                composable(ScreenRoutes.ProfileScreen().name) { ProfileScreen() }
                composable(ScreenRoutes.OrdersScreen().name) { ProfileScreen() }
                composable(ScreenRoutes.CartScreen().name) { MovieScreen() }
                composable(ScreenRoutes.ContactScreen().name) { BookScreen() }

                composable(ScreenRoutes.ProductScreen().name) { ProductScreen(productViewModel) }
                composable(ScreenRoutes.BookScreen().name) { BookScreen() }
                composable(ScreenRoutes.MovieScreen().name) { MovieScreen() }
                composable(ScreenRoutes.GameScreen().name) { MovieScreen() }


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
        modifier = Modifier.height(50.dp).background(Color(35, 7, 59)),
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
                    navigationController.navigate(ScreenRoutes.ProductScreen().name) {
                        popUpTo(0)
                    }
                }) {
                    Icon(
                        Icons.Default.List,
                        contentDescription = "Game Icon",
                        modifier = Modifier.size(40.dp),
                        tint = if (selected.value == Icons.Default.List) Color.White else Color(204, 173, 228)
                    )
                }

                IconButton(onClick = {
                    selected.value = Icons.Default.Book
                    navigationController.navigate(ScreenRoutes.BookScreen().name) {
                        popUpTo(0)
                    }
                }) {
                    Icon(
                        Icons.Default.Book,
                        contentDescription = "Book Icon",
                        modifier = Modifier.size(40.dp),
                        tint = if (selected.value == Icons.Default.Book) Color.White else Color(204, 173, 228)                    )
                }

                IconButton(onClick = {
                    selected.value = Icons.Default.Movie
                    navigationController.navigate(ScreenRoutes.MovieScreen().name) {
                        popUpTo(0)
                    }
                }) {
                    Icon(
                        Icons.Default.Movie,
                        contentDescription = "Movie Icon",
                        modifier = Modifier.size(40.dp),
                        tint = if (selected.value == Icons.Default.Movie) Color.White else Color(204, 173, 228)
                    )
                }

                IconButton(onClick = {
                    selected.value = Icons.Default.VideogameAsset
                    navigationController.navigate(ScreenRoutes.GameScreen().name) {
                        popUpTo(0)
                    }
                }) {
                    Icon(
                        Icons.Default.VideogameAsset,
                        contentDescription = "Game Icon",
                        modifier = Modifier.size(40.dp),
                        tint = if (selected.value == Icons.Default.VideogameAsset) Color.White else Color(204, 173, 228)
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


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottonBar() {
    val navigationController = rememberNavController()
    val selected = remember {
        mutableStateOf(Icons.Default.List)
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Green
            ) {
                IconButton(
                    onClick = {
                        selected.value = Icons.Default.List
                        navigationController.navigate(ScreenRoutes.ProductScreen().name) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.List,
                        contentDescription = null,
                        modifier = Modifier.size(26.dp),
                        tint = if (selected.value == Icons.Default.List) Color.White else Color.DarkGray
                    )
                }


                IconButton(
                    onClick = {
                        selected.value = Icons.Default.Book
                        navigationController.navigate(ScreenRoutes.BookScreen().name) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.Book,
                        contentDescription = null,
                        modifier = Modifier.size(26.dp),
                        tint = if (selected.value == Icons.Default.List) Color.White else Color.DarkGray
                    )
                }

                IconButton(
                    onClick = {
                        selected.value = Icons.Default.Movie
                        navigationController.navigate(ScreenRoutes.MovieScreen().name) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.Movie,
                        contentDescription = null,
                        modifier = Modifier.size(26.dp),
                        tint = if (selected.value == Icons.Default.List) Color.White else Color.DarkGray
                    )
                }

                IconButton(
                    onClick = {
                        selected.value = Icons.Default.VideogameAsset
                        navigationController.navigate(ScreenRoutes.GameScreen().name) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.VideogameAsset,
                        contentDescription = null,
                        modifier = Modifier.size(26.dp),
                        tint = if (selected.value == Icons.Default.VideogameAsset) Color.White else Color.DarkGray
                    )
                }

            }


        }
    ) {

            paddingValues ->
        NavHost(
            navController = navigationController,
            startDestination = ScreenRoutes.BookScreen().name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(ScreenRoutes.BookScreen().name) { BookScreen() }
            composable(ScreenRoutes.BookScreen().name) { BookScreen() }
            composable(ScreenRoutes.MovieScreen().name) { MovieScreen() }
            composable(ScreenRoutes.MovieScreen().name) { MovieScreen() }
        }

    }


}


/*
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer() {
    val navigationController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier
                        .background(Color(204, 173, 228))
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "")
                }

                Divider()

                NavigationDrawerItem(label = { Text(text = "Perfil") }, selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Person Icon"
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(ScreenRoutes.ProfileScreen().name) {
                            popUpTo(0) //No se dejan pantallas abiertas en segundo plano
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
                        navigationController.navigate(ScreenRoutes.ProfileScreen().name) {
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
                        navigationController.navigate(ScreenRoutes.MovieScreen().name) {
                            popUpTo(0) //No se dejan pantallas abiertas en segundo plano
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
                        navigationController.navigate(ScreenRoutes.BookScreen().name) {
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
            }
        ) {
            NavHost(navController = navigationController, startDestination = ScreenRoutes.ProfileScreen().name) {
                composable(ScreenRoutes.ProfileScreen().name){ ProfileScreen() }
                composable(ScreenRoutes.ProfileScreen().name){ ProfileScreen() }
                composable(ScreenRoutes.MovieScreen().name){ MovieScreen() }
                composable(ScreenRoutes.BookScreen().name){ BookScreen()}
            }
        }

    }

}*/