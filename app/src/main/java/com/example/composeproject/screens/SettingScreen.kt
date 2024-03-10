package com.example.composeproject.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable

@Composable
fun SettingScreen(onHomeClick: () -> Unit, onMailClick: () -> Unit) {
    ScreenContent(
        icon = Icons.Filled.Settings,
        screenText = "Setting Screen",
        firstButtonIcon = Icons.Filled.Home,
        onFirstButtonClick = onHomeClick,
        secondButtonIcon = Icons.Filled.MailOutline,
        onSecondButtonClick = onMailClick
    )
}