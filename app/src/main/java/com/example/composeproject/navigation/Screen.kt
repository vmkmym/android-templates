package com.example.composeproject.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("HomeScreen")
    data object Mail : Screen("MailScreen")
    data object Setting : Screen("SettingScreen")
}