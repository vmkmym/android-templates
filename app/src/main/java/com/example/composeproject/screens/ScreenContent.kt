package com.example.composeproject.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScreenContent(
    icon: ImageVector,
    screenText: String,
    firstButtonIcon: ImageVector,
    onFirstButtonClick: () -> Unit,
    secondButtonIcon: ImageVector,
    onSecondButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Screen Icon",
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = screenText,
            fontSize = 24.sp,
        )
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 40.dp),
        ) {
            IconButton(onClick = { onFirstButtonClick() }) {
                Icon(
                    imageVector = firstButtonIcon,
                    contentDescription = "First Button Icon",
                    modifier = Modifier.size(80.dp)
                )
            }
            Spacer(modifier = Modifier.padding(20.dp))
            IconButton(onClick = { onSecondButtonClick() }) {
                Icon(
                    imageVector = secondButtonIcon,
                    contentDescription = "Second Button Icon",
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }
}