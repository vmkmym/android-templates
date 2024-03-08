package com.example.composeproject.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MailScreen(onHomeClick: () -> Unit, onSettingClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.MailOutline,
            contentDescription = "Home Icon",
            modifier = Modifier.size(100.dp)
        )
        Text(
            text ="Mail Screen",
            fontSize = 24.sp,
        )
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 40.dp),
        ) {
            IconButton(onClick = { onHomeClick() }) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Mail Icon",
                    modifier = Modifier.size(80.dp)
                )
            }
            Spacer(modifier = Modifier.padding(20.dp))
            IconButton(onClick = { onSettingClick() }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Mail Icon",
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun MailScreenPreview() {
    MailScreen(
        onHomeClick = {},
        onSettingClick = {}
    )
}

