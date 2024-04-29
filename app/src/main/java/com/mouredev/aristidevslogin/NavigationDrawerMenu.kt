package com.mouredev.aristidevslogin

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController


class DrawerMenuNavigation(private val navController:NavHostController){
    fun navigateTo(destination: NavigationItem){
        navController.navigate(destination.route){
            popUpTo(navController.graph.findStartDestination().id){
                saveState = true
            }
            launchSingleTop = true
        }
    }
}

data class NavigationItem(
    val title : String,
    val route : String,
    val selectedIcon : ImageVector,
    val unSelectedIcon : ImageVector,
    val badgeCount : Int? = null
)

val MENU_DRAWER_ROUTES = listOf(
    NavigationItem(
        title = "Perfil",
        route = DrawerRoutes.PROFILE,
        selectedIcon = Icons.Filled.Person,
        unSelectedIcon = Icons.Filled.Person
    ),
    NavigationItem(
        title = "Pedidos",
        route = DrawerRoutes.ORDERS,
        selectedIcon = Icons.Filled.ListAlt,
        unSelectedIcon = Icons.Filled.ListAlt
    ),
    NavigationItem(
        title = "Carrito",
        route = DrawerRoutes.CART,
        selectedIcon = Icons.Filled.ShoppingCart,
        unSelectedIcon = Icons.Filled.ShoppingCart
    ),
    NavigationItem(
        title = "Contacto",
        route = DrawerRoutes.CONTACT,
        selectedIcon = Icons.Filled.Phone,
        unSelectedIcon = Icons.Filled.Phone
    )
)

object DrawerRoutes {
    const val PROFILE = "Profile"
    const val ORDERS = "Orders"
    const val CART = "Cart"
    const val CONTACT = "Contact"
}