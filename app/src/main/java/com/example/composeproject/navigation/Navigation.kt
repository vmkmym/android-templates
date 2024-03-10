package com.example.composeproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeproject.screens.HomeScreen
import com.example.composeproject.screens.MailScreen
import com.example.composeproject.screens.SettingScreen

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable("HomeScreen") {
            HomeScreen(
                onMailClick = { navController.navigate(NavigationDestination.Mail.route) },
                onSettingClick = { navController.navigate(NavigationDestination.Setting.route) }
            )
        }
        composable("MailScreen") {
            MailScreen(
                onHomeClick = { navController.navigate(NavigationDestination.Home.route) },
                onSettingClick = { navController.navigate(NavigationDestination.Setting.route) }
            )
        }
        composable("SettingScreen") {
            SettingScreen(
                onHomeClick = { navController.navigate(NavigationDestination.Home.route) },
                onMailClick = { navController.navigate(NavigationDestination.Mail.route) }
            )
        }
    }
}