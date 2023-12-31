package com.firhanalisofi.ducatimototype.screen.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object DetailType : Screen("home/{typeId}") {
        fun createRoute(typeId: Long) = "home/$typeId"
    }
}