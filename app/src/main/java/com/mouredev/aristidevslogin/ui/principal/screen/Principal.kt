package com.mouredev.aristidevslogin.ui.principal.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mouredev.aristidevslogin.BottomBarNavigation
import com.mouredev.aristidevslogin.DrawerMenuNavigation
import com.mouredev.aristidevslogin.DrawerRoutes
import com.mouredev.aristidevslogin.MENU_DRAWER_ROUTES
import com.mouredev.aristidevslogin.NavigationBottomBar
import com.mouredev.aristidevslogin.NavigationItem
import com.mouredev.aristidevslogin.Routes
import com.mouredev.aristidevslogin.TOP_LEVEL_DESTINATIONS
import com.mouredev.aristidevslogin.data.ProductsRepositoryImpl
import com.mouredev.aristidevslogin.data.RetrofitInstance
import com.mouredev.aristidevslogin.ui.home.screen.BookScreen
import com.mouredev.aristidevslogin.ui.home.screen.MovieScreen
import com.mouredev.aristidevslogin.ui.home.screen.ProductScreen
import com.mouredev.aristidevslogin.ui.home.ui.ProductsViewModel
import kotlinx.coroutines.launch
import com.mouredev.aristidevslogin.R
import com.mouredev.aristidevslogin.ScreenRoutes
import com.mouredev.aristidevslogin.ui.home.screen.ProfileScreen


@Composable
fun PrincipalScreen() {

    val navController = rememberNavController()
    val navigateAction = remember(navController) {
        BottomBarNavigation(navController)
    }
    val navigateMenuDrawer = remember(navController) {
        DrawerMenuNavigation(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination = navBackStackEntry?.destination?.route ?: Routes.PRODUCT

    Content(
        navController = navController,
        selectedDestination = selectedDestination,
        navigateMenuDrawer = navigateMenuDrawer::navigateTo,
        navigateTopLevelDestination = navigateAction::navigateTo
    )


    //NavDrawer()
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    selectedDestination: String,
    navigateMenuDrawer: (NavigationItem) -> Unit,
    navigateTopLevelDestination: (NavigationBottomBar) -> Unit
) {

    val productviewModel = ProductsViewModel(ProductsRepositoryImpl(RetrofitInstance.api))

    //Estado inicial del menú lateral
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                NavBarHeader()

                Spacer(modifier = Modifier.height(8.dp))

                DrawerMenuNav(
                    selectedDestination = selectedDestination,
                    navigateTopLevelDestination = navigateMenuDrawer
                )
                //NavDrawer()

            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )

                        }
                    }
                )
            }, bottomBar = {
                BottomBar(
                    selectedDestination = selectedDestination,
                    navigateTopLevelDestination = navigateTopLevelDestination
                )
            }
        ) { innerPadding ->

            Row(modifier = modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    NavHost(
                        modifier = Modifier.weight(1f),
                        navController = navController,
                        startDestination = Routes.PRODUCT
                    ) {

                        composable(Routes.PRODUCT) {
                            ProductScreen(productviewModel)
                        }

                        composable(Routes.BOOK) {
                            BookScreen()
                        }

                        composable(Routes.MOVIE) {
                            MovieScreen()
                        }

                        composable(Routes.GAME) {
                            MovieScreen()
                        }

                    }

                }
            }

        }
    }
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer() {
    val navigationController = rememberNavController()
    val corrutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val context = LocalContext.current.applicationContext

    val selected = remember {
        mutableStateOf(Icons.Default.Person)
    }

    val sheetState = rememberModalBottomSheetState()

    var showBottomSheet by remember {
        mutableStateOf(false)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {

            ModalDrawerSheet {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "")
                }

                Divider()

                NavigationDrawerItem(
                    label = { Text(text = "Perfil", color = Color.White) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Perfil"
                        )
                    },
                    onClick = {
                        corrutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(ScreenRoutes.ProfileScreen().name) {
                            popUpTo(0) //Cierra las anteriores pantallas de navegación
                        }
                    }
                )

                NavigationDrawerItem(
                    label = { Text(text = "Pedidos", color = Color.White) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ListAlt,
                            contentDescription = "Pedidos"
                        )
                    },
                    onClick = {
                        corrutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(ScreenRoutes.ProfileScreen().name) {
                            popUpTo(0) //Cierra las anteriores pantallas de navegación
                        }
                    }
                )

                NavigationDrawerItem(
                    label = { Text(text = "Carrito", color = Color.White) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Carrito"
                        )
                    },
                    onClick = {
                        corrutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(ScreenRoutes.ProfileScreen().name) {
                            popUpTo(0) //Cierra las anteriores pantallas de navegación
                        }
                    }
                )

                NavigationDrawerItem(
                    label = { Text(text = "Contacto", color = Color.White) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "Contacto"
                        )
                    },
                    onClick = {
                        corrutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(ScreenRoutes.ProfileScreen().name) {
                            popUpTo(0) //Cierra las anteriores pantallas de navegación
                        }
                    }
                )
            }
        },
    ) {

        Scaffold(
            topBar = {
                val coroutineScope = rememberCoroutineScope()
                TopAppBar(title = { Text(text = "Mediaflix") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Magenta,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Rounded.Menu, contentDescription = "MenuButtom")
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar(containerColor = Color.Green) {
                    IconButton(onClick = {
                        selected.value = Icons.Default.List
                        navigationController.navigate(ScreenRoutes.PrincipalScreen().name)
                    }, content = {
                        Icon(Icons.Default.Home, contentDescription = "Principal")
                    })

                    IconButton(onClick = {
                        selected.value = Icons.Default.Book
                        navigationController.navigate(ScreenRoutes.BookScreen().name)
                    }, content = {
                        Icon(Icons.Default.Home, contentDescription = "Book")
                    })

                    IconButton(onClick = {
                        selected.value = Icons.Default.Movie
                        navigationController.navigate(ScreenRoutes.MovieScreen().name)
                    }, content = {
                        Icon(Icons.Default.Home, contentDescription = "Movie")
                    })

                    IconButton(onClick = {
                        selected.value = Icons.Default.VideogameAsset
                        navigationController.navigate(ScreenRoutes.MovieScreen().name)
                    }, content = {
                        Icon(Icons.Default.Home, contentDescription = "Game")
                    })
                }
            }
        ) {

                NavHost(navController = navigationController, startDestination = ScreenRoutes.PrincipalScreen().name ){
                    composable(ScreenRoutes.PrincipalScreen().name){ PrincipalScreen()}
                    composable(ScreenRoutes.BookScreen().name){ BookScreen()}
                    composable(ScreenRoutes.MovieScreen().name){ MovieScreen()}
                    composable(ScreenRoutes.GameScreen().name){ MovieScreen()}
                    composable(ScreenRoutes.ProfileScreen().name){ ProfileScreen()}
                    composable(ScreenRoutes.OrdersScreen().name){ ProfileScreen()}
                    composable(ScreenRoutes.CartScreen().name){ ProfileScreen()}
                    composable(ScreenRoutes.ContactScreen().name){ ProfileScreen()}
                }

        }

    }
}


@Composable
fun DrawerMenuNav(
    selectedDestination: String,
    navigateTopLevelDestination: (NavigationItem) -> Unit
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        MENU_DRAWER_ROUTES.forEach { destinations ->
            NavigationBarItem(
                selected = selectedDestination == destinations.route,
                onClick = { navigateTopLevelDestination(destinations) },
                icon = {
                    Icon(
                        imageVector = destinations.selectedIcon,
                        contentDescription = ""
                    )
                }
            )
        }
    }
}


@Composable
fun BottomBar(
    selectedDestination: String,
    navigateTopLevelDestination: (NavigationBottomBar) -> Unit
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        TOP_LEVEL_DESTINATIONS.forEach { destinations ->
            NavigationBarItem(
                selected = selectedDestination == destinations.route,
                onClick = { navigateTopLevelDestination(destinations) },
                icon = {
                    Icon(
                        painter = destinations.selectedIcon(),
                        contentDescription = destinations.iconTextId
                    )
                }
            )
        }
    }
}