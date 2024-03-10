package com.example.composeproject.navigation

sealed class NavigationDestination(val route: String) {
    data object Home : NavigationDestination("HomeScreen")
    data object Mail : NavigationDestination("MailScreen")
    data object Setting : NavigationDestination("SettingScreen")
}
