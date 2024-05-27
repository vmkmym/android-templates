package com.example.composeproject.navigation

sealed class NavigationDestination(val route: String) {
    data object Home : NavigationDestination("HabitOverviewScreen")
    data object CreateNewHabit : NavigationDestination("HabitCreationScreen")
    data object HabitDetail : NavigationDestination("HabitDetailScreen")
    data object Progress : NavigationDestination("HabitProgressScreen")
    data object Setting : NavigationDestination("SettingScreen")
}