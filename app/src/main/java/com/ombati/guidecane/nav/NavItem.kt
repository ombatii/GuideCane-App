package com.ombati.guidecane.nav

import com.ombati.guidecane.R

sealed class NavItem(
    val route : String,
    val selectedIcon : Int,
    val label : String
) {
    data object Home: NavItem(route = "home", selectedIcon = R.drawable.home, label = "Home")
    data object Profile: NavItem(route = "profile", selectedIcon = R.drawable.profile, label = "Profile")
    data object Notification: NavItem(route = "notification", selectedIcon = R.drawable.notification, label = "Notification")
}