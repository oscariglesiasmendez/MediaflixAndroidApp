package com.mouredev.aristidevslogin.pruebaDrawer

import com.mouredev.aristidevslogin.R

enum class NavigationItemDrawerMenu (
    val title: String,
    val icon: Int
) {
    Home(
        icon = R.drawable.product_icon,
        title = "Home"
    ),
    Profile(
        icon = R.drawable.product_icon,
        title = "Profile"
    ),
    Premium(
        icon = R.drawable.product_icon,
        title = "Premium"
    ),
    Settings(
        icon = R.drawable.product_icon,
        title = "Settings"
    )
}