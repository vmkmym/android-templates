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
                onMailClick = { navController.navigate(Screen.Mail.route) },
                onSettingClick = { navController.navigate(Screen.Setting.route) }
            )
        }
        composable("MailScreen") {
            MailScreen(
                onHomeClick = { navController.navigate(Screen.Home.route) },
                onSettingClick = { navController.navigate(Screen.Setting.route) }
            )
        }
        composable("SettingScreen") {
            SettingScreen(
                onHomeClick = { navController.navigate(Screen.Home.route) },
                onMailClick = { navController.navigate(Screen.Mail.route) }
            )
        }
    }
}