package com.mouredev.aristidevslogin.ui.principal.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mouredev.aristidevslogin.BottomBarNavigation
import com.mouredev.aristidevslogin.NavigationBottomBar
import com.mouredev.aristidevslogin.Routes
import com.mouredev.aristidevslogin.TOP_LEVEL_DESTINATIONS
import com.mouredev.aristidevslogin.data.ProductsRepositoryImpl
import com.mouredev.aristidevslogin.data.RetrofitInstance
import com.mouredev.aristidevslogin.ui.home.screen.BookScreen
import com.mouredev.aristidevslogin.ui.home.screen.MovieScreen
import com.mouredev.aristidevslogin.ui.home.screen.ProductScreen
import com.mouredev.aristidevslogin.ui.home.ui.ProductsViewModel
import kotlinx.coroutines.launch


@Composable
fun PrincipalScreen() {

    val navController = rememberNavController()
    val navigateAction = remember(navController) {
        BottomBarNavigation(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination = navBackStackEntry?.destination?.route ?: Routes.PRODUCT


    Content(
        navController = navController,
        selectedDestination = selectedDestination,
        navigateTopLevelDestination = navigateAction::navigateTo
    )
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    selectedDestination: String,
    navigateTopLevelDestination: (NavigationBottomBar) -> Unit
) {

    val productviewModel = ProductsViewModel(ProductsRepositoryImpl(RetrofitInstance.api))

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
                Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
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