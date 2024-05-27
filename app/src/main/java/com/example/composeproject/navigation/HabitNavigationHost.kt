package com.example.composeproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeproject.view.HabitCreationScreen
import com.example.composeproject.view.HabitDetailScreen
import com.example.composeproject.view.HabitOverviewScreen
import com.example.composeproject.view.HabitProgressScreen
import com.example.composeproject.view.SettingScreen

@Composable
fun HabitNavigationHost() {
    val navController = rememberNavController()

    fun navigateTo(destination: NavigationDestination) {
        navController.navigate(destination.route)
    }

    NavHost(
        navController = navController,
        startDestination = "HabitOverviewScreen"
    ) {
        composable("HabitOverviewScreen") {
            HabitOverviewScreen(
                onCreateNewHabitClick = { navigateTo(NavigationDestination.CreateNewHabit) },
                onHabitDetailClick = { navigateTo(NavigationDestination.HabitDetail) },
                onProgressClick = { navigateTo(NavigationDestination.Progress) },
                onSettingClick = { navigateTo(NavigationDestination.Setting) }
            )
        }
        composable("HabitCreationScreen") {
            HabitCreationScreen(
                onHomeClick = { navigateTo(NavigationDestination.Home) },
                onHabitDetailClick = { navigateTo(NavigationDestination.HabitDetail) },
                onProgressClick = { navigateTo(NavigationDestination.Progress) },
                onSettingClick = { navigateTo(NavigationDestination.Setting) }
            )
        }
        composable("HabitDetailScreen") {
            HabitDetailScreen(
                onHomeClick = { navigateTo(NavigationDestination.Home) },
                onProgressClick = { navigateTo(NavigationDestination.Progress) },
                onSettingClick = { navigateTo(NavigationDestination.Setting) }
            )
        }
        composable("HabitProgressScreen") {
            HabitProgressScreen(
                onHomeClick = { navigateTo(NavigationDestination.Home) },
                onHabitDetailClick = { navigateTo(NavigationDestination.HabitDetail) },
                onSettingClick = { navigateTo(NavigationDestination.Setting) }
            )
        }
        composable("SettingScreen") {
            SettingScreen(
                onHomeClick = { navigateTo(NavigationDestination.Home) },
                onHabitDetailClick = { navigateTo(NavigationDestination.HabitDetail) },
                onProgressClick = { navigateTo(NavigationDestination.Progress) },
            )
        }
    }
}