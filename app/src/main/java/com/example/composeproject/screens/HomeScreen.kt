package com.example.composeproject.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(onMailClick: () -> Unit, onSettingClick: () -> Unit) {
    ScreenContent(
        icon = Icons.Filled.Home,
        screenText = "Home Screen",
        firstButtonIcon = Icons.Filled.MailOutline,
        onFirstButtonClick = onMailClick,
        secondButtonIcon = Icons.Filled.Settings,
        onSecondButtonClick = onSettingClick
    )
}
