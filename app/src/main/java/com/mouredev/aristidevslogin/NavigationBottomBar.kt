package com.mouredev.aristidevslogin

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mouredev.aristidevslogin.R


class BottomBarNavigation(private val navController:NavHostController){
    fun navigateTo(destination: NavigationBottomBar){
        navController.navigate(destination.route){
            popUpTo(navController.graph.findStartDestination().id){
                saveState = true
            }
            launchSingleTop = true
        }
    }
}

data class NavigationBottomBar(
    val route: String,
    val selectedIcon: @Composable () -> Painter,
    val iconTextId: String
)

val TOP_LEVEL_DESTINATIONS = listOf(
    NavigationBottomBar(
        route = Routes.PRODUCT,
        selectedIcon = { painterResource(R.drawable.product_icon) },
        iconTextId = "Book"
    ),
    NavigationBottomBar(
        route = Routes.BOOK,
        selectedIcon = { painterResource(R.drawable.book_icon) },
        iconTextId = "Book"
    ),
    NavigationBottomBar(
        route = Routes.MOVIE,
        selectedIcon = { painterResource(R.drawable.movie_icon) },
        iconTextId = "Movie"
    ),
    NavigationBottomBar(
        route = Routes.GAME,
        selectedIcon = { painterResource(R.drawable.game_icon) },
        iconTextId = "Game"
    )
)

object Routes {
    const val PRODUCT = "Product"
    const val BOOK = "Book"
    const val MOVIE = "Movie"
    const val GAME = "Movie"
}